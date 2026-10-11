package com.zosh.payload.response;

import com.zosh.enums.CabinClassType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FareResponse {
    private Long id;
    private String name;
    private Character rbdCode;
    private Long flightId;
    private Long cabinClassId;
    private CabinClassType cabinClass;

    //pricing
    private Double baseFare;
    private Double taxesAndFees;
    private Double airlineFees;
    private Double currentPrice;
    private Double totalPrice;
    private Double fareLabel;

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

    //Relationships
    private Long fareRulesId;
    private FareRulesResponse fareRule;
    private BaggagePolicyResponse baggagePolicy;

    //Audit
    private Instant createdAt;
    private Instant updatedAt;





}
