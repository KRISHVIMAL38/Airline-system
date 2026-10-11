package com.zosh.mapper;

import com.zosh.embeddable.*;
import com.zosh.model.Fare;
import com.zosh.payload.request.FareRequest;
import com.zosh.payload.response.FareResponse;

public class FareMapper {
    public static Fare toFair(FareRequest request){
        if (request == null)return null;

        Double calculatedPrice = request.getCurrentPrice();
        if(calculatedPrice == null){
            calculatedPrice = request.getBaseFare()
                            + request.getTaxesAndFees()
                            + request.getAirlineFees();
        }

        SeatBenefits seatBenefits = SeatBenefits
                .builder()
                .extraSeatSpace(bool(request.getExtraSeatSpace()))
                .preferredSeatChoice(bool(request.getPreferredSeatChoice()))
                .advanceSeatSelection(bool(request.getAdvanceSeatSelection()))
                .guaranteedSeatTogether(bool(request.getGuaranteedSeatTogether()))
                .build();

        BoardingBenefits boardingBenefits = BoardingBenefits.builder()
                .priorityBoarding(bool(request.getPriorityBoarding()))
                .priorityCheckIn(bool(request.getPriorityCheckIn()))
                .fastTrackSecurity(bool(request.getFastTrackSecurity()))
                .build();

        InFlightBenefits inFlightBenefits = InFlightBenefits.builder()
                .complimentaryMeals(bool(request.getComplimentaryMeals()))
                .premiumMealChoice(bool(request.getPremiumMealChoice()))
                .inFlightInternet(bool(request.getInFlightInternet()))
                .inFlightEntertainment(bool(request.getInFlightEntertainment()))
                .complimentaryBeverages(bool(request.getComplimentaryBeverages()))
                .build();

        FlexibilityBenefits flexibilityBenefits = FlexibilityBenefits.builder()
                .freeDateChange(bool(request.getFreeDateChange()))
                .partialRefund(bool(request.getPartialRefund()))
                .fullRefund(bool(request.getFullRefund()))
                .build();

        PremiumServiceBenefits premiumServiceBenefits = PremiumServiceBenefits.builder()
                .loungeAccess(bool(request.getLoungeAccess()))
                .airportTransfer(bool(request.getAirportTransfer()))
                .build();

        return Fare.builder()
                .name(request.getName())
                .rbdCode(request.getRbdCode())
                .flightId(request.getFlightId())
                .cabinClassId(request.getCabinClassId())
                .baseFare(request.getBaseFare())
                .taxAndFees(request.getTaxesAndFees())
                .airlineFees(request.getAirlineFees())
                .currentPrice(calculatedPrice)
                .fareLabel(request.getFareLabel())
                .seatBenefits(seatBenefits)
                .boardingBenefits(boardingBenefits)
                .inFlightBenefits(inFlightBenefits)
                .flexibilityBenefits(flexibilityBenefits)
                .premiumServiceBenefits(premiumServiceBenefits)
                .build();
    }

    private static Boolean bool(Boolean val){
        return val!=null?val : false;
    }

    public static FareResponse toResponse(Fare fare){
        if(fare == null)return null;

        return FareResponse.builder()
                .id(fare.getId())
                .name(fare.getName())
                .rbdCode(fare.getRbdCode())
                .flightId(fare.getFlightId())
                .cabinClassId(fare.getCabinClassId())
                .cabinClass(fare.getCabinClass())
                .baseFare(fare.getBaseFare())
                .taxesAndFees(fare.getTaxAndFees())
                .airlineFees(fare.getAirlineFees())
                .currentPrice(fare.getCurrentPrice())
                .totalPrice(fare.getTotalPrice())
                .fareRulesId(fare.getFareRule()!=null ? fare.getFareRule().getId():null)
                .extraSeatSpace(fare.getSeatBenefits()!=null ? fare.getSeatBenefits().getExtraSeatSpace() : false)
                .preferredSeatChoice(fare.getSeatBenefits()!=null ? fare.getSeatBenefits().getPreferredSeatChoice() : false)
                .advanceSeatSelection(fare.getSeatBenefits()!=null ? fare.getSeatBenefits().getAdvanceSeatSelection() : false)
                .guaranteedSeatTogether(fare.getSeatBenefits()!=null ? fare.getSeatBenefits().getGuaranteedSeatTogether() : false)

                .priorityBoarding(fare.getBoardingBenefits()!=null ? fare.getBoardingBenefits().getPriorityBoarding():false)
                .priorityCheckIn(fare.getBoardingBenefits()!=null ? fare.getBoardingBenefits().getPriorityCheckIn():false)
                .fastTrackSecurity(fare.getBoardingBenefits()!=null ? fare.getBoardingBenefits().getFastTrackSecurity():false)

                //InflightBenefits
                .complimentaryMeals(fare.getInFlightBenefits()!=null ? fare.getInFlightBenefits().getComplimentaryMeals():false)
                .premiumMealChoice(fare.getInFlightBenefits()!=null ? fare.getInFlightBenefits().getPremiumMealChoice():false)
                .inFlightInternet(fare.getInFlightBenefits()!=null ? fare.getInFlightBenefits().getInFlightInternet():false)
                .inFlightEntertainment(fare.getInFlightBenefits()!=null ? fare.getInFlightBenefits().getInFlightEntertainment():false)
                .complimentaryBeverages(fare.getInFlightBenefits()!=null ? fare.getInFlightBenefits().getComplimentaryMeals():false)

                //flexibility benefit
                .freeDateChange(fare.getFlexibilityBenefits()!=null ? fare.getFlexibilityBenefits().getFreeDateChange():false)
                .partialRefund(fare.getFlexibilityBenefits()!=null ? fare.getFlexibilityBenefits().getPartialRefund():false)
                .fullRefund(fare.getFlexibilityBenefits()!=null ? fare.getFlexibilityBenefits().getFullRefund():false)

                //premium services benefit
                .loungeAccess(fare.getPremiumServiceBenefits()!=null ? fare.getPremiumServiceBenefits().getLoungeAccess():false)
                .airportTransfer(fare.getPremiumServiceBenefits()!=null?
                        fare.getPremiumServiceBenefits().getAirportTransfer():false)
                .fareRule(fare.getFareRule()!=null ? FareRulesMapper.toResponse(fare.getFareRule()):null)
                .baggagePolicy(fare.getBaggagePolicy()!=null ?
                        BaggagePolicyMapper.toResponse(fare.getBaggagePolicy()):null)
                .createdAt(fare.getCreatedAt())
                .updatedAt(fare.getUpdatedAt())
                .build();
    }

    public static void updateEntity(FareRequest request , Fare existing){
        if(request == null)return;

        if(request.getName()!=null)existing.setName(request.getName());
        if(request.getRbdCode()!=null)existing.setRbdCode(request.getRbdCode());
        if(request.getFlightId()!=null)existing.setFlightId(request.getFlightId());
        if(request.getCabinClassId()!=null)existing.setCabinClassId(request.getCabinClassId());

        if (request.getBaseFare()!=null)existing.setBaseFare(request.getBaseFare());
        if(request.getTaxesAndFees()!=null)existing.setTaxAndFees(request.getTaxesAndFees());
        if(request.getAirlineFees()!=null)existing.setAirlineFees(request.getAirlineFees());
        if(request.getCurrentPrice()!=null)existing.setCurrentPrice(request.getCurrentPrice());
        if(request.getFareLabel()!=null)existing.setFareLabel(request.getFareLabel());

        //update embedded benefits
        SeatBenefits sb = new SeatBenefits();
        if(request.getExtraSeatSpace()!=null)sb.setExtraSeatSpace(request.getExtraSeatSpace());
        if(request.getPreferredSeatChoice()!=null)sb.setPreferredSeatChoice(request.getPreferredSeatChoice());
        if(request.getAdvanceSeatSelection()!=null)sb.setAdvanceSeatSelection(request.getAdvanceSeatSelection());
        if(request.getGuaranteedSeatTogether()!=null)sb.setGuaranteedSeatTogether(request.getGuaranteedSeatTogether());
        existing.setSeatBenefits(sb);

        //Boarding Benefits
        BoardingBenefits bb = new BoardingBenefits();
        if(request.getPriorityBoarding()!=null)bb.setPriorityBoarding(request.getPriorityBoarding());
        if(request.getPriorityCheckIn()!=null)bb.setPriorityCheckIn(request.getPriorityCheckIn());
        if(request.getFastTrackSecurity()!=null)bb.setFastTrackSecurity(request.getFastTrackSecurity());
        existing.setBoardingBenefits(bb);

        //In flight Benefits
        InFlightBenefits ifb = new InFlightBenefits();
        if(request.getComplimentaryMeals()!=null)ifb.setComplimentaryMeals(request.getComplimentaryMeals());
        if(request.getPremiumMealChoice()!=null)ifb.setPremiumMealChoice(request.getPremiumMealChoice());
        if(request.getInFlightInternet()!=null)ifb.setInFlightInternet(request.getInFlightInternet());
        if (request.getInFlightEntertainment()!=null)ifb.setInFlightEntertainment(request.getInFlightEntertainment());
        if(request.getComplimentaryBeverages()!=null)ifb.setComplimentaryBeverages(request.getComplimentaryBeverages());
        existing.setInFlightBenefits(ifb);

        //Flexibility Benefits
        FlexibilityBenefits fb  = new FlexibilityBenefits();
        if(request.getFreeDateChange()!=null)fb.setFreeDateChange(request.getFreeDateChange());
        if(request.getPartialRefund()!=null)fb.setPartialRefund(request.getPartialRefund());
        if(request.getFullRefund()!=null)fb.setFullRefund(request.getFullRefund());
        existing.setFlexibilityBenefits(fb);

        //PremiumService Benefits
        PremiumServiceBenefits psb = new PremiumServiceBenefits();
        if(request.getLoungeAccess()!=null)psb.setLoungeAccess(request.getLoungeAccess());
        if(request.getAirportTransfer()!=null)psb.setAirportTransfer(request.getAirportTransfer());
        existing.setPremiumServiceBenefits(psb);




    }
}
