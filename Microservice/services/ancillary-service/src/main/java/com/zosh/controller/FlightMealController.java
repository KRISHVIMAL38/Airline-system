package com.zosh.controller;

import com.zosh.payload.request.FlightMealRequest;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.FlightMealResponse;
import com.zosh.service.FlightMealService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight-meals")
public class FlightMealController {
    @Autowired
    private FlightMealService flightMealService;

    @PostMapping
    public ResponseEntity<FlightMealResponse> createFlightMeal(
           @Valid @RequestBody FlightMealRequest flightMealRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightMealService.createFlightMeal(flightMealRequest));
    }

    @PostMapping("/price/total")
    public ResponseEntity<Double> calculateMealPrice(
            @RequestBody List<Long> requests){
            return ResponseEntity.status(HttpStatus.OK).body(flightMealService.calculateMealPrice(requests));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightMealResponse> getFlightMealById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(flightMealService.getFlightMealById(id));
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<FlightMealResponse>> getMealsByFlightId(@PathVariable Long flightId){
        return ResponseEntity.status(HttpStatus.OK).body(flightMealService.getByFlightId(flightId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FlightMealResponse>> getAll(
            @RequestParam List<Long> ids){
        return ResponseEntity.status(HttpStatus.OK).body(flightMealService.getAllByIds(ids));
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<FlightMealResponse> updateFlightByMealAvailability(@PathVariable Long id,
                                                                             @RequestParam Boolean availability){
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    flightMealService.updateFlightMealAvailability(id, availability));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlightByMealId(@PathVariable Long id){
        flightMealService.deleteFlightMeal(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("flightMeal deleted successfully"));
    }
}
