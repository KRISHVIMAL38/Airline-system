package com.zosh.service;

import com.zosh.payload.request.FlightInstanceRequest;
import com.zosh.payload.response.FlightInstanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface FlightInstanceService {
    FlightInstanceResponse createFlightInstance(Long userId ,FlightInstanceRequest request) throws Exception;
    FlightInstanceResponse updateFlightInstance(Long Id, FlightInstanceRequest request) throws Exception;
    FlightInstanceResponse getFlightInstanceById(Long Id) throws Exception;
    Page<FlightInstanceResponse> getByAirlineId(Long userId,
                                                Long departureAirportId,
                                                Long arrivalAirportId,
                                                Long flightId,
                                                LocalDate onDate,
                                                Pageable pageable);
    void deleteFlightInstance(Long Id) throws Exception;


}
