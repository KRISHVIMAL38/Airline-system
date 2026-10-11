package com.zosh.service.impl;

import com.zosh.client.AirlineClient;
import com.zosh.client.LocationClient;
import com.zosh.client.PricingClient;
import com.zosh.client.SeatClient;
import com.zosh.enums.CabinClassType;
import com.zosh.mapper.FlightInstanceMapper;
import com.zosh.model.Flight;
import com.zosh.model.FlightInstance;
import com.zosh.payload.request.FlightSearchRequest;
import com.zosh.payload.response.*;
import com.zosh.repository.FlightInstanceRepository;
import com.zosh.service.FlightSearchService;
import com.zosh.service.specification.FlightInstanceSpecification;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlightSearchServiceImpl implements FlightSearchService {
    @Autowired
    private FlightInstanceRepository flightInstanceRepository;

    @Autowired
    private PricingClient pricingClient;

    @Autowired
    private SeatClient seatClient;

    @Autowired
    private AirlineClient airlineClient;

    @Autowired
    private LocationClient locationClient;

    @Override
    public Page<FlightInstanceResponse> searchFlights(
            FlightSearchRequest flightSearchRequest,
            Pageable pageable) {
        Pageable sortPageable = applySort(
                pageable,
                flightSearchRequest.getSortBy(),
                flightSearchRequest.getSortOrder());

        Specification<FlightInstance> specification =
                FlightInstanceSpecification.buildSearchSpecification(flightSearchRequest);

        Page<FlightInstance> dbPage = flightInstanceRepository.findAll(sortPageable);

        if(dbPage.isEmpty()) return  Page.empty(sortPageable);

        List<FlightInstance> instances = new ArrayList<>(dbPage.getContent());
        Map<Long, FareResponse> fareMap= Collections.emptyMap();

        if (flightSearchRequest.getCabinClass()!=null) {
            final boolean hasPriceFilter = flightSearchRequest.getMinPrice()!=null
                    && flightSearchRequest.getMaxPrice()!=null;

            Map<Long,FareResponse> mergedFareMap = new HashMap<>();

            List<FlightInstance> filtered = new ArrayList<>();

            for (FlightInstance fi : instances) {
                com.zosh.payload.response.CabinClassResponse cabinClassResponse = seatClient.getCabinClassByAircraftAndName(
                        flightSearchRequest.getCabinClass(),
                        fi.getFlight().getAircraftId());
                Long cabinClassId = cabinClassResponse.getId() ;

                if (cabinClassId!=null) continue;

                FareResponse fare = pricingClient.getLowestFareForFlightAndCabinClass(
                        fi.getFlight().getId(),
                        cabinClassId);

                if(fare==null) continue;

                if(hasPriceFilter) {
                    Double price  = fare.getTotalPrice();
                    if (price == null)continue;
                    if(price <flightSearchRequest.getMinPrice())continue;
                    if(price > flightSearchRequest.getMaxPrice())continue;
                }

                mergedFareMap.put(fi.getId(), fare);
                filtered.add(fi);
            }
            fareMap = mergedFareMap;
            instances = filtered;

            if(instances.isEmpty()) return  Page.empty(sortPageable);

        }

        List<FlightInstanceResponse> responses = enrichWithExternalData(instances,fareMap);
        return new PageImpl<>(responses,sortPageable,dbPage.getTotalElements());

    }

    private List<FlightInstanceResponse> enrichWithExternalData(
            List<FlightInstance> instances,
            Map<Long, FareResponse> fareMap) {
        Map<Long, AirlineResponse> airlineCache = new HashMap<>();
        Map<Long, AirportResponse> airportCache = new HashMap<>();
        Map<Long,AircraftResponse> aircraftCache = new HashMap<>();

        List<FlightInstanceResponse> results = new ArrayList<>(instances.size());

        for (FlightInstance fi : instances) {
            AircraftResponse aircraft = aircraftCache.computeIfAbsent(
                    fi.getFlight().getAircraftId(),airlineClient::getAirCraft
            );

            AirlineResponse airline = airlineCache.computeIfAbsent(
                    fi.getAirlineId(),airlineClient::getAirlineById
            );

            AirportResponse depAirport = airportCache.computeIfAbsent(
                    fi.getDepartureAirportId(), locationClient::getAirportById
            );

            AirportResponse arrAirport = airportCache.computeIfAbsent(
                    fi.getArrivalAirportId(), locationClient::getAirportById
            );

            FlightInstanceResponse response = FlightInstanceMapper.toResponse(
                    fi,aircraft,airline,depAirport,arrAirport
            );

            response.setFare(fareMap.get(fi.getFlight().getId()));

            results.add(response);
        }

        return results;
    }

    private Pageable applySort(Pageable pageable, String sortBy, String sortOrder) {
        Sort.Direction direction = "desc".equalsIgnoreCase(sortBy) ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = (sortBy==null || sortBy.isBlank())?
                Sort.by(direction,"departureDateTime"):
                switch (sortBy.toLowerCase()) {
                    case "arrival" -> Sort.by(direction, "arrivalDateTime");
                    case "departure" -> JpaSort.unsafe(direction,
                            "TIMESTAMPDIFF(MINUTE, departureDateTime, arrivalDateTime)");
                    default -> Sort.by(direction, "departureDateTime");
        };

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),sort);
    }
}
