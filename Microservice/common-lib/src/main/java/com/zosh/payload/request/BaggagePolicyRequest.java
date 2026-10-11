package com.zosh.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaggagePolicyRequest {
    @NotBlank(message = "Policy name is required")
    private String name;

    @NotNull(message = "fare id required")
    private Long fareId;

    private String description;

    @PositiveOrZero
    private Double cabinBaggageMaxWeight;

    private Integer cabinBaggageMaxDimensions;

    @PositiveOrZero
    private Integer cabinBaggagePieces ;

    @PositiveOrZero
    private Double cabinBaggageWeightPerPiece;

    @PositiveOrZero
    private Double checkInBaggageMaxWeight;

    @PositiveOrZero
    private Integer checkInBaggagePieces;

    @PositiveOrZero
    private Double checkInBaggageWeightPerPiece;

    @PositiveOrZero
    private Integer freeCheckedBagsAllowance;

    private Boolean priorityBaggage = false;
    private Boolean extraBaggageAllowance = false;

}
