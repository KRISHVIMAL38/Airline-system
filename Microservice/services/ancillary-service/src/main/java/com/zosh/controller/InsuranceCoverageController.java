package com.zosh.controller;

import com.zosh.model.InsuranceCoverage;
import com.zosh.payload.request.InsuranceCoverageRequest;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.InsuranceCoverageResponse;
import com.zosh.service.InsuranceCoverageService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insurance-coverages")
public class InsuranceCoverageController {
    @Autowired
    private InsuranceCoverageService insuranceCoverageService;

    @PostMapping
    public ResponseEntity<InsuranceCoverageResponse> createCoverage(
            @Valid @RequestBody InsuranceCoverageRequest request) throws EntityNotFoundException{
        return ResponseEntity.status(HttpStatus.CREATED).body(
                insuranceCoverageService.createInsuranceCoverage(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsuranceCoverageResponse> updateCoverage (
            @PathVariable Long id,
            @Valid @RequestBody InsuranceCoverageRequest request) throws EntityNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(
                insuranceCoverageService.updateInsuranceCoverageById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCoverage (@PathVariable Long id) throws EntityNotFoundException {
        insuranceCoverageService.deleteInsuranceCoverageById(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("deleted successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceCoverageResponse> getCoverageById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(insuranceCoverageService.getInsuranceCoverageById(id));
    }

    @GetMapping
    public ResponseEntity<List<InsuranceCoverageResponse>> getAllCoverage() {
        return ResponseEntity.status(HttpStatus.OK).body(insuranceCoverageService.getAllCoverages());
    }

    @GetMapping("/ancillary/{ancillaryId}")
    public ResponseEntity<List<InsuranceCoverageResponse>>  getCoverageByAncillaryId(@PathVariable Long ancillaryId) {
        return ResponseEntity.status(HttpStatus.OK).body(insuranceCoverageService.getCoverageByAncillaryId(ancillaryId));
    }

    @GetMapping("/ancillary/{ancillaryId}/active")
    public ResponseEntity<List<InsuranceCoverageResponse>>  getActiveCoverageByAncillaryId(@PathVariable Long ancillaryId) {
        return ResponseEntity.status(HttpStatus.OK).body(insuranceCoverageService.getCoverageByAncillaryId(ancillaryId));
    }
}
