package com.zosh.service;

import com.zosh.payload.request.FlightInstanceCabinRequest;
import com.zosh.payload.response.FlightInstanceCabinResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightInstanceCabinService {
    FlightInstanceCabinResponse createFlightInstanceCabin(FlightInstanceCabinRequest request) throws Exception;
    FlightInstanceCabinResponse getFlightInstanceCabin(Long id) throws Exception;
    Page<FlightInstanceCabinResponse> getByFlightInstanceId(Long id, Pageable pageable);
    FlightInstanceCabinResponse getByFlightInstanceAndCabinClassId(Long flightInstanceId,Long cabinClassId);
    FlightInstanceCabinResponse updateFlightInstanceCabin(Long id, FlightInstanceCabinRequest request);
    void deleteFlightInstanceCabin(Long id);
}
