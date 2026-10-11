package com.zosh.repository;

import com.zosh.model.FlightMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightMealRepository extends JpaRepository<FlightMeal, Long> {
    List<FlightMeal> findByFlightId(Long flightId);
    Boolean existsByFlightIdAndMealId(Long flightId,Long mealId);
}
