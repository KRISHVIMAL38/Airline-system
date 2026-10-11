package com.zosh.controller;

import com.zosh.enums.FlightStatus;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.FlightRequest;
import com.zosh.payload.response.FlightResponse;
import com.zosh.service.FlightService;
import com.zosh.service.impl.FlightServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    @Autowired
   private FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightResponse> createFlight(
            @Valid @RequestBody FlightRequest request, @RequestHeader("X-User-Id") Long userId) throws Exception {
                return ResponseEntity.status(HttpStatus.CREATED).body(flightService.createFlight(userId,request));
    }

    @GetMapping("/{Id}")
    public ResponseEntity<FlightResponse> getFlightById(@PathVariable("Id") Long Id) throws Exception {
        return ResponseEntity.ok(flightService.getFlightById(Id));
    }

    @GetMapping("/airline")
    public ResponseEntity<Page<FlightResponse>> getFlightsByAirline(@RequestHeader("X-User-Id") Long userId,
                                                                    @RequestParam(required = false) Long departureAirportId,
                                                                    @RequestParam(required = false) Long arrivalAirportId,
                                                                    Pageable page){
        return ResponseEntity.ok(flightService.getFlightsByAirline(userId,departureAirportId,
                arrivalAirportId,page));
    }

    @PutMapping("/{Id}")
    public ResponseEntity<FlightResponse> updateFlight(@PathVariable Long Id,
                                                       @Valid @RequestBody FlightRequest request) throws Exception {
        return ResponseEntity.ok(flightService.updateFlight(Id,request));
    }

    @PatchMapping("/{Id}")
    public ResponseEntity<FlightResponse> changeStatus(@PathVariable Long Id,
                                                       @RequestParam FlightStatus status) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(flightService.changeStatus(Id,status));
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<ApiResponse> deleteFlight(@PathVariable Long Id,
                                                    @RequestHeader("X-User-Id") Long userId) throws Exception {
        flightService.deleteFlight(Id,userId);
        ApiResponse response = new ApiResponse("Flight deleted successfully");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }


}
