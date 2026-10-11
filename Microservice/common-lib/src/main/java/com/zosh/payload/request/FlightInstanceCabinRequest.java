package com.zosh.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightInstanceCabinRequest {

    @NotNull(message = "flightId is required")
    private Long flightId;

    @NotNull(message = "flightInstanceId is required")
    private Long flightInstanceId;

    @NotNull
    private Long cabinClassId;

    @Positive
    @NotNull
    private Double baseFare;

    @NotNull
    @PositiveOrZero
    private Double taxAndFees;

    @NotNull
    @PositiveOrZero
    private Double airlineFees;

    private Double currentPrice;
    private Boolean isActive;

}
