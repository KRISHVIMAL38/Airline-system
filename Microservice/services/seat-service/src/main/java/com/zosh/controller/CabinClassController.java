package com.zosh.controller;

import com.zosh.enums.CabinClassType;
import com.zosh.payload.request.CabinClassRequest;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.CabinClassResponse;
import com.zosh.service.CabinClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cabin-classes")
public class CabinClassController {
    @Autowired
    private CabinClassService cabinClassService;

    @PostMapping
    public ResponseEntity<CabinClassResponse> createCabinClass(
            @Valid @RequestBody CabinClassRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(cabinClassService.createCabinClass(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CabinClassResponse>  getCabinClassById(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(cabinClassService.getCabinClassById(id));
    }

    @GetMapping("/aircraft/{id}/name/{cabinClass}")
    public ResponseEntity<CabinClassResponse>  getCabinClassByAircraftAndName(
            @PathVariable CabinClassType cabinClass,
            @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(cabinClassService.getCabinClassByAircraftAndName(id,cabinClass));
    }

    @GetMapping("/aircraft/{aircraftId}")
    public ResponseEntity<List<CabinClassResponse>> getCabinClassesByAircraftId(
            @PathVariable Long aircraftId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(cabinClassService.getCabinClassesByAircraftId(aircraftId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CabinClassResponse> updateCabinClass(
            @PathVariable Long id, @RequestBody CabinClassRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(cabinClassService.updateCabinClass(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCabinClass(@PathVariable Long id) throws Exception {
        cabinClassService.deleteCabinClassById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ApiResponse("cabin Class with id  = "+id+" deleted successfully"));
    }
}
