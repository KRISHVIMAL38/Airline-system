package com.zosh.service.impl;

import com.zosh.client.AirlineClient;
import com.zosh.client.LocationClient;
import com.zosh.event.FlightInstanceCreatedEvent;
import com.zosh.event.FlightInstanceEventProducer;
import com.zosh.mapper.FlightInstanceMapper;
import com.zosh.model.Flight;
import com.zosh.model.FlightInstance;
import com.zosh.payload.request.FlightInstanceRequest;
import com.zosh.payload.response.AircraftResponse;
import com.zosh.payload.response.AirlineResponse;
import com.zosh.payload.response.AirportResponse;
import com.zosh.payload.response.FlightInstanceResponse;
import com.zosh.repository.FlightInstanceRepository;
import com.zosh.repository.FlightRepository;
import com.zosh.service.FlightInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class FlightInstanceServiceImpl implements FlightInstanceService {
    @Autowired
    private FlightInstanceRepository flightInstanceRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirlineClient airlineClient;

    @Autowired
    private LocationClient locationClient;

    @Autowired
    private FlightInstanceEventProducer  flightInstanceEventProducer;

    @Override
    public FlightInstanceResponse createFlightInstance(Long userId, FlightInstanceRequest request) throws Exception {

        AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);
        // watch airlineId
        Flight flight = flightRepository.findById(request.getFlightId()).orElseThrow(
                ()->new Exception("Flight not found"));

        //get Aircraft data from airline core service
        AircraftResponse aircraft = airlineClient.getAirCraft(flight.getAircraftId());

        FlightInstance flightInstance = FlightInstanceMapper.toInstance(request,flight);
        flightInstance.setTotalSeats(aircraft.getTotalSeats());
        flightInstance.setAvailableSeats(aircraft.getTotalSeats());

        FlightInstance saved = flightInstanceRepository.save(flightInstance);

        //create seat instances
        //publish kafka events ,seat service consume hat and create seat instance

        FlightInstanceCreatedEvent flightInstanceCreatedEvent = FlightInstanceCreatedEvent.builder()
                .flightInstanceId(flightInstance.getId())
                .aircraftId(flight.getAircraftId())
                .flightId(flight.getId())
                .build();
        flightInstanceEventProducer.sendFlightInstanceCreatedEvent(flightInstanceCreatedEvent);

        return convertToFlightInstanceResponse(saved);
    }

    @Override
    public FlightInstanceResponse updateFlightInstance(Long Id, FlightInstanceRequest request) throws Exception {
        FlightInstance existing  = flightInstanceRepository.findById(Id)
                .orElseThrow(()->new Exception("Flight Instance not found with Id = "+Id));

        FlightInstanceMapper.updateEntity(request,existing);

        FlightInstance saved = flightInstanceRepository.save(existing);
        return convertToFlightInstanceResponse(saved);
    }

    @Override
    public FlightInstanceResponse getFlightInstanceById(Long Id) throws Exception {
        FlightInstance fi = flightInstanceRepository.findById(Id).orElseThrow(()->
                new Exception("Flight Instance not found with id = "+Id));
        return convertToFlightInstanceResponse(fi);
    }

    @Override
    public Page<FlightInstanceResponse> getByAirlineId(Long userId,
                                                       Long departureAirportId,
                                                       Long arrivalAirportId,
                                                       Long flightId,
                                                       LocalDate onDate,
                                                       Pageable pageable) {
        //fetch by ownerId
        AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);

        LocalDateTime start = onDate!=null ? onDate.atStartOfDay():null;
        LocalDateTime end = onDate!=null ? onDate.plusDays(1).atStartOfDay():null;

        return flightInstanceRepository.findByAirlineId(
                airlineResponse.getId(),departureAirportId,arrivalAirportId,flightId,start,end,pageable
        ).map(this::convertToFlightInstanceResponse);
    }

    @Override
    public void deleteFlightInstance(Long Id) throws Exception {
        FlightInstance existing  = flightInstanceRepository.findById(Id)
                .orElseThrow(()->new Exception("Flight Instance not found with Id = "+Id));

        flightInstanceRepository.delete(existing);
    }

    private FlightInstanceResponse convertToFlightInstanceResponse(FlightInstance flightInstance){
        //service to service communication
        AirlineResponse airlineResponse = airlineClient.getAirlineById(flightInstance.getAirlineId());

        AirportResponse departureAirport = locationClient.getAirportById(flightInstance.getDepartureAirportId());

        AirportResponse arrivalAirport =  locationClient.getAirportById(flightInstance.getArrivalAirportId());

        AircraftResponse aircraftResponse = airlineClient.getAirCraft(flightInstance.getFlight().getAircraftId());

        return FlightInstanceMapper.toResponse(
                flightInstance,aircraftResponse,
                airlineResponse,departureAirport,
                arrivalAirport);
    }
}
