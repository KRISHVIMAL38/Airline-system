package com.zosh.mapper;

import com.zosh.enums.FlightStatus;
import com.zosh.model.Flight;
import com.zosh.payload.response.*;

public class FlightMapper {
    public static Flight toFlight(FlightRequest request){
        if(request== null)return null;

        return Flight.builder()
                .flightNumber(request.getFlightNumber())
                .aircraftId(request.getAircraftId())
                .departureAirportId(request.getDepartureAirportId())
                .arrivalAirportId(request.getArrivalAirportId())
                .build();
    }

    public static FlightResponse toResponse(Flight flight, AircraftResponse aircraftResponse,
                                            AirlineResponse airlineResponse, AirportResponse departureAirport,
                                            AirportResponse arrivalAirport){
        if(flight == null)return null;

        return FlightResponse.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airline(airlineResponse)
                .aircraft(aircraftResponse)
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .status(flight.getStatus())
                .createdAt(flight.getCreatedAt())
                .updatedAt(flight.getUpdatedAt())
                .build();
    }

    public static void updateEntity(FlightRequest request, Flight existingFlight){
        if(request == null)return ;

        if(request.getFlightNumber()!=null)existingFlight.setFlightNumber(request.getFlightNumber());
        if(request.getAircraftId()!=null)existingFlight.setAircraftId(request.getAircraftId());
        if(request.getDepartureAirportId()!=null)existingFlight.setDepartureAirportId(request.getDepartureAirportId());
        if(request.getArrivalAirportId()!=null)existingFlight.setArrivalAirportId(request.getArrivalAirportId());
        if(request.getStatus()!=null)existingFlight.setStatus(request.getStatus());

    }
}
