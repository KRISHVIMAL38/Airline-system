package com.zosh.service.impl;

import com.zosh.client.AirlineClient;
import com.zosh.mapper.MealMapper;
import com.zosh.model.Meal;
import com.zosh.payload.request.MealRequest;
import com.zosh.payload.response.AirlineResponse;
import com.zosh.payload.response.MealResponse;
import com.zosh.repository.MealRepository;
import com.zosh.service.MealService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private AirlineClient airlineClient;

    @Override
    public MealResponse createMeal(Long userId,MealRequest request) throws Exception {
        AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);

        if(mealRepository.existsByCodeAndAirlineId(
                request.getCode(), airlineResponse.getId())){
            throw new Exception("Meal already exists with given code and airlineId");
        }

        Meal meal = Meal.builder()
                .code(request.getCode())
                .name(request.getName())
                .mealType(request.getMealType())
                .dietaryRestriction(request.getDietaryRestriction())
                .ingredients(request.getIngredients())
                .imageUrl(request.getImageUrl())
                .requiredAdvancedBookings(request.getRequiredAdvanceBooking()!=null ?
                        request.getRequiredAdvanceBooking():false)
                .advancedBookingsHours(request.getAdvanceBookingHours())
                .displayOrder(request.getDisplayOrder()!=null ? request.getDisplayOrder():0)
                .airlineId(airlineResponse.getId())
                .build();

       Meal saved= mealRepository.save(meal);

       return MealMapper.toResponse(saved);

    }

    @Override
    public MealResponse getMealById(Long id)  throws EntityNotFoundException{
        Meal existing =  mealRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Meal not found with id: " + id));

        return MealMapper.toResponse(existing);
    }

    @Override
    public MealResponse updateMeal(Long userId,Long id, MealRequest request) throws EntityNotFoundException,Exception{
        AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);

        Meal existing =  mealRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Meal not found with id: " + id));

        if(mealRepository.existsByCodeAndAirlineIdAndIdNot(existing.getCode(), airlineResponse.getId(), id)){
            throw new Exception("meal code already exists");
        }

        existing.setCode(request.getCode()!=null ? request.getCode() : existing.getCode());
        existing.setName(request.getName()!=null ? request.getName() : existing.getName());
        existing.setMealType(request.getMealType()!=null ? request.getMealType() : existing.getMealType());
        existing.setDietaryRestriction(request.getDietaryRestriction()!=null ? request.getDietaryRestriction() : existing.getDietaryRestriction());
        existing.setIngredients(request.getIngredients()!=null ? request.getIngredients() : existing.getIngredients());
        existing.setImageUrl(request.getImageUrl()!=null ? request.getImageUrl() : existing.getImageUrl());
        existing.setAdvancedBookingsHours(request.getAdvanceBookingHours()!=null ? request.getAdvanceBookingHours() : existing.getAdvancedBookingsHours());
        existing.setDisplayOrder(request.getDisplayOrder()!=null ? request.getDisplayOrder() : existing.getDisplayOrder());
        return MealMapper.toResponse(mealRepository.save(existing));
    }

    @Override
    public List<MealResponse> getMealByAirlineId(Long userId) {
        AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);

        return mealRepository.findByAirlineId(airlineResponse.getId())
                .stream().map(MealMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteMealById(Long id) throws EntityNotFoundException{
        Meal existing =  mealRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Meal not found with id: " + id));
        mealRepository.delete(existing);
    }

    @Override
    public MealResponse updateMealAvailability(Long id, Boolean availability) throws EntityNotFoundException{
        Meal existing =  mealRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Meal not found with id: " + id));

        existing.setAvailable(availability);
        return MealMapper.toResponse(mealRepository.save(existing));
    }
}
