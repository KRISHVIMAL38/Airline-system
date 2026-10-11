package com.zosh.service;

import com.zosh.payload.request.MealRequest;
import com.zosh.payload.response.MealResponse;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface MealService {
    MealResponse createMeal(Long userId,MealRequest request) throws Exception;
    MealResponse getMealById(Long id) throws EntityNotFoundException;
    MealResponse updateMeal(Long userId,Long id,MealRequest request) throws EntityNotFoundException, Exception;
    List<MealResponse> getMealByAirlineId(Long userId);
    void deleteMealById(Long id) throws EntityNotFoundException;
    MealResponse updateMealAvailability
            (Long id,Boolean availability)  throws EntityNotFoundException, Exception;
}
