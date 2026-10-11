package com.zosh.mapper;

import com.zosh.model.BaggagePolicy;
import com.zosh.model.Fare;
import com.zosh.payload.request.BaggagePolicyRequest;
import com.zosh.payload.response.BaggagePolicyResponse;

public class BaggagePolicyMapper {

    public static BaggagePolicy toEntity(BaggagePolicyRequest request, Fare fare){
        if(request == null)return null;

        return BaggagePolicy.builder()
                .fare(fare)
                .name(request.getName())
                .description(request.getDescription())
                .cabinBaggageMaxWeight(request.getCabinBaggageMaxWeight())
                .cabinBaggagePieces(request.getCabinBaggagePieces()!=null ? request.getCabinBaggagePieces() : 0)
                .cabinBaggageWeightPerPiece(request.getCabinBaggageWeightPerPiece())
                .cabinBaggageMaxDimensions(request.getCabinBaggageMaxDimensions())
                .checkInBaggageMaxWeight(request.getCheckInBaggageMaxWeight())
                .checkInBaggagePieces(request.getCheckInBaggagePieces()!=null ? request.getCheckInBaggagePieces() : 0)
                .checkInBaggageWeightPerPiece(request.getCheckInBaggageWeightPerPiece())
                .freeCheckedBagsAllowance(request.getFreeCheckedBagsAllowance()!=null ? request.getFreeCheckedBagsAllowance() : 0)
                .priorityBaggage(request.getPriorityBaggage()!=null ? request.getPriorityBaggage() : false)
                .extraBaggageAllowance(request.getExtraBaggageAllowance()!=null ? request.getExtraBaggageAllowance():false)
                .build();
    }
    public static BaggagePolicyResponse toResponse(BaggagePolicy policy){
        if(policy == null)return null;

        return BaggagePolicyResponse.builder()
                .id(policy.getId())
                .name(policy.getName())
                .description(policy.getDescription())
                .cabinBaggageMaxWeight(policy.getCabinBaggageMaxWeight())
                .cabinBaggagePieces(policy.getCabinBaggagePieces())
                .cabinBaggageWeightPerPiece(policy.getCabinBaggageWeightPerPiece())
                .cabinBaggageMaxDimension(policy.getCabinBaggageMaxDimensions())
                .checkInBaggageMaxWeight(policy.getCheckInBaggageMaxWeight())
                .checkInBaggagePieces(policy.getCheckInBaggagePieces())
                .checkInBaggageWeightPerPiece(policy.getCheckInBaggageWeightPerPiece())
                .freeCheckedBagsAllowance(policy.getFreeCheckedBagsAllowance())
                .priorityBaggage(policy.getPriorityBaggage())
                .extraBaggageAllowance(policy.getExtraBaggageAllowance())
                .airlineId(policy.getAirlineId())
                .build();
    }

    public static void updateEntity(BaggagePolicyRequest request, BaggagePolicy existing){
        if (request == null)return;

        if(request.getName()!=null)existing.setName(request.getName());
        if(request.getDescription()!=null)existing.setDescription(request.getDescription());
        if(request.getCabinBaggageMaxWeight()!=null)existing.setCabinBaggageMaxWeight(request.getCabinBaggageMaxWeight());
        if(request.getCabinBaggagePieces()!=null)existing.setCabinBaggagePieces(request.getCabinBaggagePieces());
        if(request.getCabinBaggageWeightPerPiece()!=null)existing.setCabinBaggageWeightPerPiece(request.getCabinBaggageWeightPerPiece());
        if(request.getCabinBaggageMaxDimensions()!=null)existing.setCabinBaggageMaxDimensions(request.getCabinBaggageMaxDimensions());
        if(request.getCheckInBaggageMaxWeight()!=null)existing.setCheckInBaggageMaxWeight(request.getCheckInBaggageMaxWeight());
        if(request.getCheckInBaggagePieces()!=null)existing.setCheckInBaggagePieces(request.getCheckInBaggagePieces());
        if(request.getCheckInBaggageWeightPerPiece()!=null)existing.setCheckInBaggageWeightPerPiece(request.getCheckInBaggageWeightPerPiece());
        if(request.getFreeCheckedBagsAllowance()!=null)existing.setFreeCheckedBagsAllowance(request.getFreeCheckedBagsAllowance());
        if(request.getPriorityBaggage()!=null)existing.setPriorityBaggage(request.getPriorityBaggage());
        if(request.getExtraBaggageAllowance()!=null)existing.setExtraBaggageAllowance(request.getExtraBaggageAllowance());
    }
}
