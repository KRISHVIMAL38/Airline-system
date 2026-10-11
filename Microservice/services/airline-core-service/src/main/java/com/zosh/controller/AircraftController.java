package com.zosh.controller;

import com.zosh.payload.request.AircraftRequest;
import com.zosh.payload.response.AircraftResponse;
import com.zosh.payload.response.ApiResponse;
import com.zosh.service.AircraftService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircrafts")
public class AircraftController {
    @Autowired
    private AircraftService aircraftService;

    @PostMapping
    public ResponseEntity<AircraftResponse> createAircraft(
            @Valid @RequestBody AircraftRequest request,
            @RequestHeader("X-User-Id") Long userId) throws Exception {

        AircraftResponse aircraft = aircraftService.createAircraft(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(aircraft);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<AircraftResponse> getAirCraft(@PathVariable Long Id) throws Exception {
        return ResponseEntity.ok(aircraftService.getAircraftById(Id));
    }

    @GetMapping()
    public ResponseEntity<List<AircraftResponse>> getAirCraftByOwnerId(
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.ok(aircraftService.listAllAircraftByOwner(userId));
    }

    @PutMapping("/{Id}")
    public ResponseEntity<AircraftResponse> updateAircraft(
            @PathVariable("Id") Long Id, @Valid @RequestBody AircraftRequest request,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.ok(aircraftService.updateAircraft(Id,request,userId));
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<ApiResponse> deleteAircraft(
            @PathVariable("Id") Long aircraftId,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        aircraftService.deleteAircraft(aircraftId,userId);
        ApiResponse response = new ApiResponse("Aircraft deleted successfully");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
