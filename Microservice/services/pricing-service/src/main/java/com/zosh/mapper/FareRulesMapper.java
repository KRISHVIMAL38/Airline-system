package com.zosh.mapper;

import com.zosh.model.Fare;
import com.zosh.model.FareRules;
import com.zosh.payload.request.FareRulesRequest;
import com.zosh.payload.response.FareRulesResponse;

public class FareRulesMapper {
    public static FareRules toFareRules(FareRulesRequest request, Fare fare){
        if(request == null)return null;

        return FareRules.builder()
                .ruleName(request.getRuleName())
                .fare(fare)
                .airlineId(request.getAirlineId())
                .isRefundable(request.getIsRefundable())
                .changeFee(request.getChangeFee())
                .cancellationFee(request.getCancellationFee())
                .changeDeadlineHours(request.getChangeDeadlineHours())
                .refundDeadlineDays(request.getRefundDeadlineDays())
                .isChangeable(request.getIsChangeable()!=null ? request.getIsChangeable():false)
                .build();
    }

    public static FareRulesResponse toResponse(FareRules rules){
        if(rules == null)return null;

        return FareRulesResponse.builder()
                .id(rules.getId())
                .ruleName(rules.getRuleName())
                .fareId(rules.getFare()!=null ? rules.getFare().getId() : null)
                .airlineId(rules.getAirlineId())
                .isRefundable(rules.getIsRefundable())
                .changeFee(rules.getChangeFee())
                .cancellationFee(rules.getCancellationFee())
                .refundDeadlineDays(rules.getRefundDeadlineDays())
                .changeDeadlineHours(rules.getChangeDeadlineHours())
                .isChangeable(rules.getIsChangeable())
                .createdAt(rules.getCreatedAt())
                .updatedAt(rules.getUpdatedAt())
                .build();
    }

    public static void updateEntity(FareRulesRequest request, FareRules existing){
        if (request == null)return;

        if(request.getRuleName()!=null)existing.setRuleName(request.getRuleName());
        if(request.getAirlineId()!=null)existing.setAirlineId(request.getAirlineId());
        if(request.getIsRefundable()!=null)existing.setIsRefundable(request.getIsRefundable());
        if(request.getChangeFee()!=null)existing.setChangeFee(request.getChangeFee());
        if(request.getCancellationFee()!=null)existing.setCancellationFee(request.getCancellationFee());
        if(request.getRefundDeadlineDays()!=null)existing.setRefundDeadlineDays(request.getRefundDeadlineDays());
        if(request.getChangeDeadlineHours()!=null)existing.setChangeDeadlineHours(request.getChangeDeadlineHours());
        if(request.getIsChangeable()!=null)existing.setIsChangeable(request.getIsChangeable());

    }
}
