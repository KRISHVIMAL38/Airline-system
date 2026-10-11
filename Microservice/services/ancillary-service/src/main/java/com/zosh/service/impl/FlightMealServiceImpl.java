package com.zosh.service.impl;

import com.zosh.mapper.FlightMealMapper;
import com.zosh.model.FlightMeal;
import com.zosh.model.Meal;
import com.zosh.payload.request.FlightMealRequest;
import com.zosh.payload.response.FlightMealResponse;
import com.zosh.repository.FlightMealRepository;
import com.zosh.repository.MealRepository;
import com.zosh.service.FlightMealService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightMealServiceImpl implements FlightMealService {
    @Autowired
    private FlightMealRepository flightMealRepository;

    @Autowired
    private MealRepository mealRepository;

    @Override
    public FlightMealResponse createFlightMeal(FlightMealRequest request) throws Exception {
        Meal meal = mealRepository.findById(request.getMealId()).orElseThrow(()->
                new EntityNotFoundException("meal not found with id = "+request.getMealId())
        ) ;

        if(flightMealRepository.existsByFlightIdAndMealId(request.getFlightId(),meal.getId())){
            throw new Exception("meal already exists for flight");
        }

        FlightMeal flightMeal = FlightMeal.builder()
                .flightId(request.getFlightId())
                .meal(meal)
                .available(request.getAvailable())
                .price(request.getPrice())
                .displayOrder(request.getDisplayOrder()!=null?request.getDisplayOrder():0)
                .build();

        FlightMeal saved = flightMealRepository.save(flightMeal);

        return FlightMealMapper.toResponse(saved);
    }

    @Override
    public FlightMealResponse updateFlightMeal(Long id, FlightMealRequest request) throws EntityNotFoundException{
        FlightMeal flightMeal = flightMealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("meal not found with id = "+id));

        flightMeal.setFlightId(request.getFlightId()!=null?request.getFlightId():flightMeal.getFlightId());
        if (request.getMealId() != null) {
            Meal meal = mealRepository.findById(request.getMealId()).orElseThrow(() ->
                    new EntityNotFoundException("meal not found with id = "+request.getMealId()));

            flightMeal.setMeal(meal);
        }

        flightMeal.setAvailable(request.getAvailable()!=null?request.getAvailable():flightMeal.getAvailable());
        flightMeal.setPrice(request.getPrice()!=null?request.getPrice():flightMeal.getPrice());
        flightMeal.setDisplayOrder(request.getDisplayOrder()!=null?request.getDisplayOrder():flightMeal.getDisplayOrder());
        FlightMeal updated = flightMealRepository.save(flightMeal);

        return FlightMealMapper.toResponse(updated);
    }

    @Override
    public FlightMealResponse updateFlightMealAvailability(Long id, Boolean availability) throws EntityNotFoundException {
        FlightMeal flightMeal = flightMealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("meal not found with id = "+id));

        flightMeal.setAvailable(availability!=null?availability:flightMeal.getAvailable());
        FlightMeal changed = flightMealRepository.save(flightMeal);
        return FlightMealMapper.toResponse(changed);
    }

    @Override
    public void deleteFlightMeal(Long id) {
        FlightMeal flightMeal = flightMealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("meal not found with id = "+id));

        flightMealRepository.delete(flightMeal);
    }

    @Override
    public FlightMealResponse getFlightMealById(Long id) {
        FlightMeal flightMeal = flightMealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("meal not found with id = "+id));

        return FlightMealMapper.toResponse(flightMeal);
    }

    @Override
    public List<FlightMealResponse> getByFlightId(Long flightId) {
        List<FlightMeal> meals = flightMealRepository.findByFlightId(flightId);
        return meals.stream().map(FlightMealMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<FlightMealResponse> getAllByIds(List<Long> ids) {
        return flightMealRepository.findAllById(ids).
                stream()
                .map(FlightMealMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Double calculateMealPrice(List<Long> mealIds) {
        List<FlightMeal> meals = flightMealRepository.findAllById(mealIds);
        Double mealPrice = 0.0;
        for (FlightMeal meal : meals) {
            mealPrice+=meal.getPrice();
        }
        return mealPrice;
    }
}
