package com.zosh.controller;

import com.zosh.payload.request.MealRequest;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.MealResponse;
import com.zosh.service.MealService;
import com.zosh.service.impl.MealServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {
    @Autowired
    private MealServiceImpl mealService;

    @PostMapping
    public ResponseEntity<MealResponse> createMeal(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody MealRequest mealRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                mealService.createMeal(userId,mealRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealResponse> getMealById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(mealService.getMealById(id));
    }

    @GetMapping("/airline")
    public ResponseEntity<List<MealResponse>> getMealsByAirlineId(
            @RequestHeader("X-User-Id") Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(mealService.getMealByAirlineId(userId));
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<MealResponse> updateMealAvailability(
        @PathVariable Long id,
        @RequestParam Boolean availability
    ){
        return ResponseEntity.status(HttpStatus.OK).body(mealService.updateMealAvailability(id, availability));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealResponse> updateMeal(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id,
            @RequestBody MealRequest request
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(
                mealService.updateMeal(userId,id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteMealById( @PathVariable Long id) throws EntityNotFoundException {
        mealService.deleteMealById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("meal successfully deleted for id: " + id));
    }


}
