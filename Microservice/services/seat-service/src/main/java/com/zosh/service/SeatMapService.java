package com.zosh.service;

import com.zosh.model.SeatMap;
import com.zosh.payload.request.SeatMapRequest;
import com.zosh.payload.response.SeatMapResponse;
import com.zosh.payload.response.SeatResponse;

public interface SeatMapService {
    SeatMapResponse creatSeatMap(Long userId, SeatMapRequest request) throws Exception;
    SeatMapResponse getSeatMapById(Long id) throws Exception;
    SeatMapResponse getSeatMapByCabinClassId(Long cabinClassId);
    SeatMapResponse updateSeatMap(Long id, SeatMapRequest request) throws Exception;
    void deleteSeatMap(Long id) throws Exception;

}
