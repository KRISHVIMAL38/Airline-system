package com.zosh.service;

import com.zosh.model.Fare;
import com.zosh.payload.request.FareRequest;
import com.zosh.payload.response.FareResponse;

import java.util.List;
import java.util.Map;

public interface FareService {
    public FareResponse createFare(FareRequest request) throws Exception;
    public FareResponse getFairById(Long Id) throws Exception;
    List<FareResponse> getFairByFlightIdAndCabinClassId(
            Long flightId,Long cabinClassId) throws Exception;
    FareResponse updateFair(Long Id,FareRequest request) throws Exception;
    void deleteFare(Long Id) throws Exception;
    List<Fare> getAllFares();

    Map<Long,FareResponse> getLowestFarePerFlight(
            List<Long> flightIds, Long cabinClassId
    );

    Map<Long,FareResponse> getFaresByIds(List<Long>ids);

    FareResponse getLowestFareForFlightAndCabinClass(
             Long flightId,
             Long cabinClassId);
}
