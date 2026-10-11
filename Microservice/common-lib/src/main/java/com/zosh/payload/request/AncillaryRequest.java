package com.zosh.payload.request;

import com.zosh.domain.AncillaryMetadata;
import com.zosh.enums.AncillaryType;
import com.zosh.payload.response.InsuranceCoverageResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AncillaryRequest {
    @NotNull(message = "ancillary type is required")
    private AncillaryType ancillaryType;

    @Size(max = 100 ,message = "subType can not be more than 100 characters")
    private String subtype;

    @Size(max = 100 ,message = "rfic code can not be more than 100 characters")
    private String rfic;

    @NotBlank(message = "message is required")
    @Size(max = 200 ,message = "name can not be more than 100 characters")
    private String name;

    @Size(max = 100 ,message = "description can not be more than 100 characters")
    private String description;

    @Size(max = 500 ,message = "subType can not be more than 100 characters")
    private String iconUrl;

    private AncillaryMetadata metadata;
    private Integer displayOrder;
}
