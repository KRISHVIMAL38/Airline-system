package com.zosh.service;

import com.zosh.model.InsuranceCoverage;
import com.zosh.payload.request.InsuranceCoverageRequest;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.InsuranceCoverageResponse;

import java.util.List;

public interface InsuranceCoverageService {
    InsuranceCoverageResponse createInsuranceCoverage(InsuranceCoverageRequest insuranceCoverage);
    InsuranceCoverageResponse getInsuranceCoverageById(Long id);
    InsuranceCoverageResponse updateInsuranceCoverageById(Long id,InsuranceCoverageRequest request);
    ApiResponse deleteInsuranceCoverageById(Long id);
    List<InsuranceCoverageResponse> getCoverageByAncillaryId(Long ancillaryId);
    List<InsuranceCoverageResponse> getActiveCoverageByAncillaryId(Long ancillaryId);
    List<InsuranceCoverageResponse> getAllCoverages();
}
