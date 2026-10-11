package com.zosh.payload.request;

import com.zosh.enums.CoverageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceCoverageRequest {

    @NotNull(message = "Ancillary ID is required")
    private Long ancillaryId;

    @NotNull(message = "Coverage type is required")
    private CoverageType coverageType;

    @NotBlank(message = "coverage name is required")
    @Size(message = "coverage name must be less than 200 characters" ,max = 200)
    private String name;

    @Size(message = "coverage description must be less than 1000 characters" ,max = 1000)
    private String description;

    @NotNull(message = "coverage Amount is required")
    @PositiveOrZero(message = "coverage amount must be grater than or equal to zero")
    private Double coverageAmount;

    private Boolean isFlat;

    @Size(message = "claim condition must be less than 500 characters" ,max = 500)
    private String claimCondition;

    @Size(message = "Emergency Contact must be less than 100 characters" ,max = 100)
    private String emergencyContact;

    private Integer displayOrder;

    private Boolean active;
}
