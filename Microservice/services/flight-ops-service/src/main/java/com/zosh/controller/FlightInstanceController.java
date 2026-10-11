package com.zosh.controller;

import com.zosh.payload.request.FlightInstanceRequest;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.FlightInstanceResponse;
import com.zosh.service.FlightInstanceService;
import com.zosh.service.impl.FlightInstanceServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/flight-instances")
public class FlightInstanceController {
    @Autowired
    private FlightInstanceService flightInstanceService;

    @PostMapping
    public ResponseEntity<FlightInstanceResponse> createFlightInstanceResponse(
            @RequestHeader("X-User-Id")Long userId,
            @Valid @RequestBody FlightInstanceRequest request
            ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                flightInstanceService.createFlightInstance(userId,request));
    }

    @GetMapping("/{Id}")
    public ResponseEntity<FlightInstanceResponse> getFlightInstanceById(
            @PathVariable Long Id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(
                flightInstanceService.getFlightInstanceById(Id));
    }

    @GetMapping
    public ResponseEntity<Page<FlightInstanceResponse>> getByAirlineId(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            @RequestParam(required = false) Long flightId,
            @RequestParam(required = false) LocalDate onDate,
            Pageable pageable
            ){
        return ResponseEntity.status(HttpStatus.OK).body(
                flightInstanceService.getByAirlineId(userId,departureAirportId,arrivalAirportId,flightId,
                        onDate,pageable
        ));
    }

    @PutMapping("/{Id}")
    public ResponseEntity<FlightInstanceResponse> updateFlightInstance(
            @PathVariable Long Id,
            //we have removed valid cause some of the fields may be empty
            @RequestBody FlightInstanceRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(flightInstanceService.
                        updateFlightInstance(Id,request));
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<ApiResponse> deleteFlightInstance(@PathVariable Long Id) throws Exception {
        flightInstanceService.deleteFlightInstance(Id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse("Flight Instance deleted successfully"));
    }
}
