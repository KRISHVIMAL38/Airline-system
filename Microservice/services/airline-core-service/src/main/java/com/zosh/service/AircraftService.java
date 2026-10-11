package com.zosh.service;

import com.zosh.payload.request.AircraftRequest;
import com.zosh.payload.response.AircraftResponse;

import java.util.List;

public interface AircraftService {
    AircraftResponse createAircraft(AircraftRequest request,Long ownerId) throws Exception;
    AircraftResponse getAircraftById(Long Id) throws Exception;
    List<AircraftResponse> listAllAircraftByOwner(Long ownerId) throws Exception;
    AircraftResponse updateAircraft(Long Id,AircraftRequest request, Long ownerId) throws Exception;
    void deleteAircraft(Long id,Long ownerId) throws Exception;
}
