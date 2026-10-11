package com.zosh.payload.response;

import com.zosh.domain.AncillaryMetadata;
import com.zosh.enums.AncillaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class AncillaryResponse {

    private Long id;
    private AncillaryType ancillaryType;
    private String subtype;
    private String rfic;
    private String name;
    private String description;
    private String categoryDisplayName;
    private String categoryIcon;;
    private String iconUrl;
    private AncillaryMetadata metadata;

    private List<InsuranceCoverageResponse> coverages;
    private Integer displayOrder;
    private Long airlineId;

}
