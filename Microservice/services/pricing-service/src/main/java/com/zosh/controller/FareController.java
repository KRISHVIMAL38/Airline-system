package com.zosh.controller;

import com.zosh.model.Fare;
import com.zosh.payload.request.FareRequest;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.FareResponse;
import com.zosh.service.FareService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fares")
public class FareController {

    @Autowired
    private FareService fareService;

    @PostMapping
    public ResponseEntity<FareResponse> createFare(
            @Valid @RequestBody FareRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(fareService.createFare(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FareResponse> getFairById(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(fareService.getFairById(id));
    }

    @GetMapping
    public  ResponseEntity<List<Fare>> getAllFares(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(fareService.getAllFares());
    }

    @PostMapping("/batch-by-ids")
    public ResponseEntity<Map<Long,FareResponse>> getFaresByIds(@RequestBody List<Long> ids){
        return ResponseEntity.status(HttpStatus.OK).body(fareService.getFaresByIds(ids));
    }

    @PostMapping("/search")
    public ResponseEntity<Map<Long,FareResponse>> getLowestFairPerFlight(
            @RequestBody List<Long> ids,
            @RequestParam Long cabinClassId){
        return ResponseEntity.status(HttpStatus.OK).body(
                fareService.getLowestFarePerFlight(ids,cabinClassId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFairById(@PathVariable Long id) throws Exception {
        fareService.deleteFare(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("fare with ID = "+id+" deleted successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FareResponse> updateFare(
            @PathVariable Long id,
            @RequestBody FareRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(fareService.updateFair(id,request));
    }

    @GetMapping("/flight/{flightId}/cabin-class/{cabinClassId}")
    public ResponseEntity<List<FareResponse>> getFareByFlightIdAndCabinClassId(
            @PathVariable Long flightId,
            @PathVariable Long cabinClassId) throws Exception {
            return ResponseEntity.status(HttpStatus.OK).body(
                    fareService.getFairByFlightIdAndCabinClassId(flightId,cabinClassId));
    }

    @GetMapping("/lowest/flight/{flightId}/cabin-class/{cabinClassId}")
    public ResponseEntity<FareResponse> getLowestFareForFlightAndCabinClass(
            @PathVariable Long flightId,
            @PathVariable Long cabinClassId){
        return ResponseEntity.status(HttpStatus.OK).body(
                fareService.getLowestFareForFlightAndCabinClass(flightId,cabinClassId)
        );
    }
}
