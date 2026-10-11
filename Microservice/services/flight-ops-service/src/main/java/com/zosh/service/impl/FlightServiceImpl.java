package com.zosh.service.impl;

import com.zosh.client.AirlineClient;
import com.zosh.client.LocationClient;
import com.zosh.enums.FlightStatus;
import com.zosh.mapper.FlightMapper;
import com.zosh.model.Flight;
import com.zosh.payload.response.*;
import com.zosh.repository.FlightRepository;
import com.zosh.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirlineClient airlineClient;

    @Autowired
    private LocationClient locationClient;

    @Override
    public FlightResponse createFlight(Long userId, FlightRequest flightRequest) throws Exception {

        AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);

        if(flightRepository.existsByFlightNumber(flightRequest.getFlightNumber())){
            throw new Exception("Flight with given flight number already exists");
        }

        Flight flight = FlightMapper.toFlight(flightRequest);
        flight.setAirlineId(airlineResponse.getId());

        Flight savedFlight = flightRepository.save(flight);
        return convertToResponse(savedFlight);
    }

    @Override
    public Page<FlightResponse> getFlightsByAirline(Long userId, Long departureAirportId, Long arrivalAirportId, Pageable pageable) {

        AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);
        return flightRepository.findByAirlineId(airlineResponse.getId(),
                departureAirportId,arrivalAirportId,pageable)
                .map(this::convertToResponse);
    }

    @Override
    public FlightResponse getFlightById(Long flightId) throws Exception {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(()->new Exception("Flight does not exists with id = "+flightId));

        return convertToResponse(flight);
    }

    @Override
    public FlightResponse updateFlight(Long flightId, FlightRequest flightRequest) throws Exception {
        Flight existing = flightRepository.findById(flightId)
                .orElseThrow(()->new Exception("Flight does not exists with id = "+flightId));

        if(flightRequest.getFlightNumber()!=null &&
        flightRepository.existsByFlightNumberAndIdNot(flightRequest.getFlightNumber(),flightId)){
            throw new Exception("Flight with given data already exists");
        }
        FlightMapper.updateEntity(flightRequest,existing);
        Flight updatedFlight = flightRepository.save(existing);
        return convertToResponse(updatedFlight);
    }

    @Override
    public FlightResponse changeStatus(Long flightId, FlightStatus status) throws Exception {
        Flight existing = flightRepository.findById(flightId)
                .orElseThrow(()->new Exception("Flight does not exists with id = "+flightId));
        existing.setStatus(status);
        Flight updatedFlight = flightRepository.save(existing);
        return convertToResponse(updatedFlight);
    }

    @Override
    public void deleteFlight(Long userId ,Long flightId) throws Exception {
        AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);

        Flight existing = flightRepository.findByAirlineIdAndId(airlineResponse.getId(),flightId)
                .orElseThrow(()->new Exception("Flight does not exists with id = "+flightId));

        flightRepository.delete(existing);
    }

    public FlightResponse convertToResponse(Flight flight){
        //todo service to service communication


        AircraftResponse aircraftResponse = airlineClient.getAirCraft(flight.getAircraftId());
        AirlineResponse airlineResponse = airlineClient.getAirlineById(flight.getAirlineId());
        AirportResponse departureAirport = locationClient.getAirportById(flight.getDepartureAirportId());
        AirportResponse arrivalAirport = locationClient.getAirportById(flight.getArrivalAirportId());

        return FlightMapper.toResponse(flight,aircraftResponse,airlineResponse
        ,departureAirport,arrivalAirport);
    }
}
