package com.zosh.mapper;

import com.zosh.model.Ancillary;
import com.zosh.model.InsuranceCoverage;
import com.zosh.payload.request.InsuranceCoverageRequest;
import com.zosh.payload.response.InsuranceCoverageResponse;

public class InsuranceCoverageMapper {
    public static InsuranceCoverage toEntity(InsuranceCoverageRequest insuranceCoverage,
                                             Ancillary ancillary) {
        if (insuranceCoverage == null) return null;

        return InsuranceCoverage.builder()
                .ancillary(ancillary)
                .coverageType(insuranceCoverage.getCoverageType())
                .name(insuranceCoverage.getName())
                .description(insuranceCoverage.getDescription())
                .coverageAmount(insuranceCoverage.getCoverageAmount())
                .isFlat(insuranceCoverage.getIsFlat()!=null ? insuranceCoverage.getIsFlat():false)
                .claimCondition(insuranceCoverage.getClaimCondition())
                .emergencyContact(insuranceCoverage.getEmergencyContact())
                .displayOrder(insuranceCoverage.getDisplayOrder())
                .active(insuranceCoverage.getActive()!=null ? insuranceCoverage.getActive():false)
                .build();
    }

    public static InsuranceCoverageResponse toResponse(InsuranceCoverage entity) {
        if (entity == null) return null;

        return InsuranceCoverageResponse.builder()
                .id(entity.getId())
                .ancillaryId(entity.getAncillary().getId())
                .ancillaryName(entity.getAncillary().getName())
                .coverageType(entity.getCoverageType())
                .name(entity.getName())
                .description(entity.getDescription())
                .coverageAmount(entity.getCoverageAmount())
                .isFlat(entity.getIsFlat()!=null ? entity.getIsFlat():false)
                .claimsCondition(entity.getClaimCondition())
                .emergencyContact(entity.getEmergencyContact())
                .displayOrder(entity.getDisplayOrder())
                .active(entity.getActive())
                .build();
    }

    public static void updateEntity(InsuranceCoverage entity,
                                    InsuranceCoverageRequest request,
                                    Ancillary ancillary) {
            if (entity == null || request == null) return;

            if (ancillary!=null)entity.setAncillary(ancillary);
            if (request.getCoverageType()!=null)entity.setCoverageType(request.getCoverageType());
            if (request.getName()!=null)entity.setName(request.getName());
            if (request.getDescription()!=null)entity.setDescription(request.getDescription());
            if (request.getCoverageAmount()!=null)entity.setCoverageAmount(request.getCoverageAmount());
            if (request.getIsFlat()!=null)entity.setIsFlat(request.getIsFlat());
            if (request.getClaimCondition()!=null)entity.setClaimCondition(request.getClaimCondition());
            if (request.getEmergencyContact()!=null)entity.setEmergencyContact(request.getEmergencyContact());
            if (request.getDisplayOrder()!=null)entity.setDisplayOrder(request.getDisplayOrder());
            if (request.getActive()!=null)entity.setActive(request.getActive());
    }

}
