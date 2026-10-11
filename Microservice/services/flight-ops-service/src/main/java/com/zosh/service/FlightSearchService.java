package com.zosh.service;

import com.zosh.payload.request.FlightSearchRequest;
import com.zosh.payload.response.FlightInstanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightSearchService {
    Page<FlightInstanceResponse> searchFlights(FlightSearchRequest flightSearchRequest, Pageable pageable);
}
