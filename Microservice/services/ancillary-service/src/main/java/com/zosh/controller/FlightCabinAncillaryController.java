package com.zosh.controller;

import com.zosh.enums.AncillaryType;
import com.zosh.payload.request.FlightCabinAncillaryRequest;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.FlightCabinAncillaryResponse;
import com.zosh.service.FlightCabinAncillaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight-cabin-ancillaries")
public class FlightCabinAncillaryController {
    @Autowired
    private FlightCabinAncillaryService flightCabinAncillaryService;

    @PostMapping
    public ResponseEntity<FlightCabinAncillaryResponse> create(
            @Valid @RequestBody FlightCabinAncillaryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightCabinAncillaryService.createFlightCabinAncillary(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightCabinAncillaryResponse> getFlightCabinAncillaryById( @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(flightCabinAncillaryService.getById(id));
    }

    @GetMapping("/flight/{flightId}/cabin/{cabinClassId}")
    public ResponseEntity<List<FlightCabinAncillaryResponse>> getAllByFlightIdAndCabinClassId(
            @PathVariable Long flightId,
            @PathVariable Long cabinClassId){
        return ResponseEntity.status(HttpStatus.OK).body(
                flightCabinAncillaryService.getByFlightAndCabinClass(flightId, cabinClassId));
    }

    @GetMapping("/flight/{flightId}/cabin/{cabinClassId}/type/{type}")
    public ResponseEntity<FlightCabinAncillaryResponse>  getByFlightIdAndCabinClassIdAndAncillaryType(
            @PathVariable Long flightId,
            @PathVariable Long cabinClassId,
            @PathVariable AncillaryType type){
        return ResponseEntity.status(HttpStatus.OK).body(
                flightCabinAncillaryService.getByFlightIdAndCabinClassIdAndType(flightId, cabinClassId, type));
    }

    @GetMapping("/flight/{flightId}/cabin/{cabinClassId}/type/{type}/all")
    public ResponseEntity<List<FlightCabinAncillaryResponse>>  getAllByFlightIdAndCabinClassIdAndAncillaryType(
            @PathVariable Long flightId,
            @PathVariable Long cabinClassId,
            @PathVariable AncillaryType type){
        return ResponseEntity.status(HttpStatus.OK).body(
                flightCabinAncillaryService.getAllByFlightIdAndCabinClassIdAndType(flightId, cabinClassId, type));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightCabinAncillaryResponse> updateFlightCabinAncillary(
            @PathVariable Long id, @RequestBody FlightCabinAncillaryRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(flightCabinAncillaryService.updateFlightCabinAncillary(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        flightCabinAncillaryService.deleteFlightCabinAncillary(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully deleted FlightCabinAncillary with id = "+id));
    }

    @PostMapping("/price/total")
    public ResponseEntity<Double> calculateAncillariesPrice(
            @RequestBody List<Long> flightCabinAncillaryIds){
        return ResponseEntity.status(HttpStatus.OK).body(flightCabinAncillaryService.calculateAncillaryPrice(flightCabinAncillaryIds));
    }

}
