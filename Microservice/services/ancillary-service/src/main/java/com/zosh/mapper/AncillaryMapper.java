package com.zosh.mapper;

import com.zosh.model.Ancillary;
import com.zosh.payload.request.AncillaryRequest;
import com.zosh.payload.response.AncillaryResponse;
import com.zosh.payload.response.InsuranceCoverageResponse;

import java.util.List;

public class AncillaryMapper {
    public static AncillaryResponse toResponse(Ancillary request,
                                             List<InsuranceCoverageResponse> covers) {
        if(request == null) return null;

        return AncillaryResponse.builder()
                .id(request.getId())
                .ancillaryType(request.getType())
                .subtype(request.getSubType())
                .rfic(request.getRfic())
                .description(request.getDescription())
                .coverages(covers)
                .displayOrder(request.getDisplayOrder())
                .airlineId(request.getAirlineId())
                .name(request.getName())
                .metadata(request.getAncillaryMetaData())
                .build();
    }

    public static Ancillary toEntity(AncillaryRequest request,Long airlineId) {
        if(request == null) return null;

        return Ancillary.builder()
                .Type(request.getAncillaryType())
                .subType(request.getSubtype())
                .rfic(request.getRfic())
                .displayOrder(request.getDisplayOrder())
                .airlineId(airlineId)
                .description(request.getDescription())
                .name(request.getName())
                .ancillaryMetaData(request.getMetadata())
                .build();
    }
}
