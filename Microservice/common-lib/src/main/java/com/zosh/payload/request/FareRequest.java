package com.zosh.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FareRequest {
    @NotBlank(message = "fare name is required")
    private String name;

    @NotNull(message = "rbd code is required")
    private Character rbdCode;

    @NotNull(message = "flight Id is required")
    private Long flightId;

    @NotNull(message = "cabin class is required")
    private Long cabinClassId;

    @NotNull(message = "Base Fare is required")
    @Positive
    private Double baseFare;

    private Double taxesAndFees;
    private Double airlineFees;
    private Double currentPrice;

    @Size(max = 100)
    private String fareLabel;

    //seat benefits
    private Boolean extraSeatSpace;
    private Boolean preferredSeatChoice;
    private Boolean advanceSeatSelection;
    private Boolean guaranteedSeatTogether;

    //Boarding Benefits
    private Boolean priorityBoarding;
    private Boolean priorityCheckIn;
    private Boolean fastTrackSecurity;

    //In-Flight Benefits
    private Boolean complimentaryMeals;
    private Boolean premiumMealChoice;
    private Boolean inFlightInternet;
    private Boolean inFlightEntertainment;
    private Boolean complimentaryBeverages;

    //Flexibility Benefits
    private Boolean freeDateChange;
    private Boolean partialRefund;
    private Boolean fullRefund;

    //premium Service Benefits
    private Boolean loungeAccess;
    private Boolean airportTransfer;
}
