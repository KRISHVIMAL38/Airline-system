package com.zosh.controller;

import com.zosh.payload.request.BaggagePolicyRequest;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.BaggagePolicyResponse;
import com.zosh.service.BaggagePolicyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baggage-policies")
public class BaggagePolicyController {
    @Autowired
    private BaggagePolicyService baggagePolicyService;

    @PostMapping
    public ResponseEntity<BaggagePolicyResponse> createBaggagePolicy(
            @Valid @RequestBody BaggagePolicyRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(baggagePolicyService.createBaggagePolicy(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaggagePolicyResponse> getBaggagePolicyById(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(baggagePolicyService.getBaggagePolicyById(id));
    }

    @GetMapping("/fare/{fareId}")
    public ResponseEntity<BaggagePolicyResponse> getBaggagePolicyByFareId(@PathVariable Long fareId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(baggagePolicyService.getBaggagePolicyByFareId(fareId));
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<BaggagePolicyResponse>> getBaggagePolicyByAirlineId(
            @PathVariable Long airlineId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(baggagePolicyService.getBaggagePoliciesByAirlineId(airlineId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaggagePolicyResponse> updateBaggagePolicy(
            @PathVariable Long id, @RequestBody BaggagePolicyRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(baggagePolicyService.updateBaggagePolicy(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBaggagePolicy(@PathVariable Long id) throws Exception {
        baggagePolicyService.deleteBaggagePolicy(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse("BaggagePolicy Deleted successfully"));
    }
}
