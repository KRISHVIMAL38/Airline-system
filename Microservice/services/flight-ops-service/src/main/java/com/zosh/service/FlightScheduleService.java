package com.zosh.service;

import com.zosh.payload.request.FlightScheduleRequest;
import com.zosh.payload.response.FlightScheduleResponse;

import java.util.List;

public interface FlightScheduleService {
    FlightScheduleResponse createFlightSchedule(Long userId,
                                                FlightScheduleRequest request) throws Exception;

    FlightScheduleResponse getFlightScheduleById(Long Id) throws Exception;
    List<FlightScheduleResponse> getFlightScheduleByAirline(Long userId);
    FlightScheduleResponse updateFlightSchedule(Long Id,
                                                FlightScheduleRequest request) throws Exception;
    void deleteFlightScheduleById(Long Id) throws Exception;
}
