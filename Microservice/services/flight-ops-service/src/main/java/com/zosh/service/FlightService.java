package com.zosh.service;

import com.zosh.enums.FlightStatus;
import com.zosh.payload.response.FlightRequest;
import com.zosh.payload.response.FlightResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightService {
    FlightResponse createFlight(Long userId, FlightRequest flightRequest) throws Exception;
    Page<FlightResponse> getFlightsByAirline(Long userId, Long departureAirportId,
                                             Long arrivalAirportId, Pageable page);
    FlightResponse getFlightById(Long flightId) throws Exception;
    FlightResponse updateFlight(Long flightId, FlightRequest flightRequest) throws Exception;
    FlightResponse changeStatus(Long flightId, FlightStatus status) throws Exception;
    void deleteFlight(Long userId ,Long flightId) throws Exception;
}
