package com.zosh.controller;

import com.zosh.enums.AirlineStatus;
import com.zosh.mapper.AirlineMapper;
import com.zosh.payload.request.AirlineRequest;
import com.zosh.payload.response.AirlineDropdownItem;
import com.zosh.payload.response.AirlineResponse;
import com.zosh.payload.response.ApiResponse;
import com.zosh.service.AirlineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
public class AirlineController {

    @Autowired
    AirlineService airlineService;

    @PostMapping()
    public ResponseEntity<AirlineResponse> createAirline(
            @Valid @RequestBody AirlineRequest request,
            @RequestHeader("X-User-Id") Long ownerId){
        AirlineResponse response = airlineService.createAirline(request,ownerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/admin")
    public ResponseEntity<AirlineResponse> getAirlineByOwner(
            @RequestHeader("X-User-Id") Long Id) throws Exception {
        AirlineResponse response = airlineService.getAirlineByOwner(Id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<AirlineResponse> getAirlineById(
            @PathVariable("Id") Long Id) throws Exception {
        AirlineResponse response = airlineService.getAirlineById(Id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<Page<AirlineResponse>> getAllAirlines(
            Pageable pageable) throws Exception {
        return ResponseEntity.ok(airlineService.getAllAirlines(pageable));
    }

    @GetMapping("/dropdown")
    public ResponseEntity<List<AirlineDropdownItem>> getAllAirlines() throws Exception {
        return ResponseEntity.ok(airlineService.getAirlineDropdown());
    }

    @PutMapping()
    public ResponseEntity<AirlineResponse> updateAirline(
            @Valid @RequestBody AirlineRequest request,
            @RequestHeader("X-User-Id") Long ownerId) throws Exception {
        return ResponseEntity.ok(airlineService.updateAirline(request,ownerId));
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<ApiResponse> deleteAirline(
            @PathVariable Long Id,
            @RequestHeader("X-User-Id") Long ownerId) throws Exception {
        airlineService.deleteAirline(Id,ownerId);
        ApiResponse response = new ApiResponse("Airline deleted successfully");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @PostMapping("/{Id}/approve")
    public ResponseEntity<AirlineResponse> approveAirline(@PathVariable Long Id) throws Exception {
        return ResponseEntity.ok(airlineService.changeStatusByAdmin(Id, AirlineStatus.ACTIVE));
    }

    @PostMapping("/{Id}/suspend")
    public ResponseEntity<AirlineResponse> suspend(@PathVariable Long Id) throws Exception {
        return ResponseEntity.ok(airlineService.changeStatusByAdmin(Id, AirlineStatus.INACTIVE));
    }

    @PostMapping("/{Id}/ban")
    public ResponseEntity<AirlineResponse> ban(@PathVariable Long Id) throws Exception {
        return ResponseEntity.ok(airlineService.changeStatusByAdmin(Id, AirlineStatus.BANNED));
    }

}
