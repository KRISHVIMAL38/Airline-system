package com.zosh.service;

import com.zosh.payload.request.FlightMealRequest;
import com.zosh.payload.response.FlightMealResponse;

import java.util.List;

public interface FlightMealService {
    FlightMealResponse createFlightMeal(FlightMealRequest request) throws Exception;
    FlightMealResponse updateFlightMeal(Long id,FlightMealRequest request);
    FlightMealResponse updateFlightMealAvailability(Long id , Boolean availability);
    void deleteFlightMeal(Long id);
    FlightMealResponse getFlightMealById(Long id);
    List<FlightMealResponse> getByFlightId(Long flightId);
    List<FlightMealResponse> getAllByIds(List<Long> ids);
    Double calculateMealPrice(List<Long> mealIds);
}
