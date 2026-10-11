package com.zosh.repository;

import com.zosh.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByAirlineId(Long airlineId);
    Boolean existsByCodeAndAirlineId(String code, Long airlineId);
    Boolean existsByCodeAndAirlineIdAndIdNot(String code, Long airlineId,Long id);

}
