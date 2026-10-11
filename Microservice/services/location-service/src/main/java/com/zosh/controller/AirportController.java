package com.zosh.controller;

import com.zosh.model.AirportMapper;
import com.zosh.payload.request.AirportRequest;
import com.zosh.payload.response.AirportResponse;
import com.zosh.payload.response.ApiResponse;
import com.zosh.service.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/airports")
public class AirportController {
    @Autowired
    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<AirportResponse> createAirport(
            @Valid @RequestBody AirportRequest request) throws Exception{
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(airportService.createAirport(request));
    }

    @GetMapping()
    public ResponseEntity<List<AirportResponse>> getAllAirports(){
        return ResponseEntity.status(HttpStatus.OK).body(airportService.getAllAirports());
    }
    @GetMapping("/{id}")
    public ResponseEntity<AirportResponse> getAirportById(@PathVariable Long id) throws Exception{
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(airportService.getAirportById(id));
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<AirportResponse>> getAirportsByCityId(@PathVariable Long cityId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(airportService.getAirportByCityId(cityId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirportResponse> updateAirport(
            @PathVariable Long id,
            @Valid @RequestBody AirportRequest request) throws Exception{
        return ResponseEntity.status(HttpStatus.OK)
                .body(airportService.updateAirport(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAirport(@PathVariable Long id) throws Exception{
        airportService.deleteAirport(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse("Airport deleted successfully"));
    }


}
