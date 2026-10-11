package com.zosh.mapper;

import com.zosh.enums.FlightStatus;
import com.zosh.model.Flight;
import com.zosh.model.FlightInstance;
import com.zosh.payload.request.FlightInstanceRequest;
import com.zosh.payload.response.AircraftResponse;
import com.zosh.payload.response.AirlineResponse;
import com.zosh.payload.response.AirportResponse;
import com.zosh.payload.response.FlightInstanceResponse;

public class FlightInstanceMapper {
    public static FlightInstance toInstance(FlightInstanceRequest request,
                                            Flight flight){
        if(flight == null)return null;
        return FlightInstance.builder()
                .flight(flight)
                .airlineId(request.getAirlineId())
                .scheduledId(request.getScheduleId())
                .departureAirportId(request.getDepartureAirportId()!=null ? request.getArrivalAirportId() : 0)
                .arrivalAirportId(request.getArrivalAirportId()!=null ? request.getArrivalAirportId() : 0)
                .departureDateTime(request.getDepartureDateTime())
                .arrivalDateTime(request.getArrivalDateTime())
                .status(FlightStatus.SCHEDULED)
                .minAdvancedBookingDays(request.getMinAdvanceBookingDays())
                .maxAdvancedBookingDays(request.getMaxAdvanceBookingDays())
                .isActive(request.getIsActive()!=null ? request.getIsActive() : false)
                .build();
    }

    public static FlightInstanceResponse toResponse(FlightInstance fi,
                                                    AircraftResponse aircraftResponse,
                                                    AirlineResponse airline,
                                                    AirportResponse departureAirport,
                                                    AirportResponse arrivalAirport){

        if(fi == null)return null;

        return FlightInstanceResponse.builder()
                .id(fi.getId())
                .flightId(fi.getFlight()!=null ? fi.getFlight().getId():null)
                .flightNumber(fi.getFlight()!=null ? fi.getFlight().getFlightNumber():null)
                .aircraftId(fi.getFlight().getAircraftId())
                .aircraftModel(aircraftResponse.getModel())
                .aircraftCode(aircraftResponse.getCode())
                .airlineId(fi.getAirlineId())
                .airlineName(airline.getName())
                .airlineLogo(airline.getLogoUrl())
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureDateTime(fi.getDepartureDateTime())
                .arrivalDateTime(fi.getArrivalDateTime())
                .formattedDuration(fi.getFormattedDuration())
                .totalSeats(fi.getTotalSeats())
                .availableSeats(fi.getAvailableSeats())
                .status(fi.getStatus())
                .minAdvanceBookingDays(fi.getMinAdvancedBookingDays())
                .maxAdvanceBookingDays(fi.getMaxAdvancedBookingDays())
                .isActive(fi.getIsActive())
                .build();
    }

    public static  void updateEntity(FlightInstanceRequest request, FlightInstance existing){
        if(request == null)return;

        if(request.getDepartureAirportId()!=null)existing.setDepartureAirportId(request.getDepartureAirportId());
        if(request.getArrivalAirportId()!=null)existing.setArrivalAirportId(request.getArrivalAirportId());
        if(request.getDepartureDateTime()!=null)existing.setDepartureDateTime(request.getDepartureDateTime());
        if(request.getArrivalDateTime()!=null)existing.setArrivalDateTime(request.getArrivalDateTime());
        if(request.getAvailableSeats()!=null)existing.setAvailableSeats(request.getAvailableSeats());
        if(request.getStatus()!=null)existing.setStatus(request.getStatus());
        if(request.getMinAdvanceBookingDays()!=null)existing.setMinAdvancedBookingDays(request.getMinAdvanceBookingDays());
        if(request.getMaxAdvanceBookingDays()!=null)existing.setMaxAdvancedBookingDays(request.getMaxAdvanceBookingDays());
        if(request.getIsActive()!=null)existing.setIsActive(request.getIsActive());

    }
}
