package com.zosh.controller;

import com.zosh.payload.request.FlightInstanceCabinRequest;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.FlightInstanceCabinResponse;
import com.zosh.service.FlightInstanceCabinService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flight-instances-cabins")
public class FlightInstanceCabinController {
    @Autowired
    private FlightInstanceCabinService flightInstanceCabinService;

    @PostMapping
    public ResponseEntity<FlightInstanceCabinResponse> createFlightInstanceCabin(
            @RequestBody @Valid FlightInstanceCabinRequest request
            ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                flightInstanceCabinService.createFlightInstanceCabin(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightInstanceCabinResponse> getFlightInstanceCabinById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(flightInstanceCabinService.getFlightInstanceCabin(id));
    }

    @GetMapping("/flight-instance/{flightInstanceId}/cabin-class/{cabinClassId}")
    public ResponseEntity<FlightInstanceCabinResponse> getFlightInstanceIdAndCabinClassId(
            @PathVariable Long flightInstanceId,
            @PathVariable Long cabinClassId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(flightInstanceCabinService.getByFlightInstanceAndCabinClassId(
                        flightInstanceId,cabinClassId
                ));
    }

    @GetMapping("/flight-instance/{flightInstanceId}")
    public ResponseEntity<Page<FlightInstanceCabinResponse>> getByFlightInstanceId(
            @PathVariable Long flightInstanceId, Pageable pageable) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(flightInstanceCabinService.getByFlightInstanceId(flightInstanceId,pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightInstanceCabinResponse> updateFlightInstanceCabin(
            @PathVariable Long id,@RequestBody FlightInstanceCabinRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(flightInstanceCabinService.updateFlightInstanceCabin(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlightInstanceCabin(
            @PathVariable Long id) throws Exception {
        flightInstanceCabinService.deleteFlightInstanceCabin(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse("Flight Instance deleted successfully"));
    }
}
