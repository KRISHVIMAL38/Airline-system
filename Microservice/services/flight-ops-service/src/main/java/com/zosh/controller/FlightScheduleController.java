package com.zosh.controller;

import com.zosh.payload.request.FlightScheduleRequest;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.FlightScheduleResponse;
import com.zosh.service.FlightScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flight-schedules")
public class FlightScheduleController {
    @Autowired
    private FlightScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<FlightScheduleResponse> createFlightSchedule(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody FlightScheduleRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createFlightSchedule(userId,request));
    }

    @GetMapping("/{Id}")
    public ResponseEntity<FlightScheduleResponse> getFlightScheduleById(@PathVariable Long Id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getFlightScheduleById(Id));
    }

    @GetMapping()
    public ResponseEntity<FlightScheduleResponse> getFlightScheduleByAirlineId(
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getFlightScheduleById(userId));
    }

    @PutMapping("/{Id}")
    public ResponseEntity<FlightScheduleResponse> updateFlightSchedule(
            @PathVariable Long Id,
            @RequestBody FlightScheduleRequest request) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateFlightSchedule(Id,request));
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<ApiResponse> deleteFlightSchedule(@PathVariable Long Id)throws Exception{
        scheduleService.deleteFlightScheduleById(Id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("flight schedule deleted successfully"));
    }

}
