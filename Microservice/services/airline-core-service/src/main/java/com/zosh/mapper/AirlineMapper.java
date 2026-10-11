package com.zosh.mapper;

import com.zosh.embeddable.Support;
import com.zosh.model.Airline;
import com.zosh.payload.request.AirlineRequest;
import com.zosh.payload.response.AirlineResponse;

public class AirlineMapper {
    public static Airline toAirline(AirlineRequest req, Long ownerId){
        if(req==null)return null;

        Airline airline = Airline.builder()
                .iataCode(req.getIataCode())
                .icaoCode(req.getIcaoCode())
                .name(req.getName())
                .alias(req.getAlias())
                .logoUrl(req.getLogoUrl())
                .website(req.getWebsite())
                .status(req.getStatus())
                .alliance(req.getAlliance())
                .headquarterCityId(req.getHeadquarterCityId())
                .ownerId(ownerId)
                .build();

        if(req.getSupportedEmail()!=null ||
           req.getSupportedPhone()!=null ||
           req.getSupportedHours()!=null){
            airline.setSupport(
                    Support
                            .builder()
                            .email(req.getSupportedEmail())
                            .phone(req.getSupportedPhone())
                            .hours(req.getSupportedHours())
                            .build());
        }

        return airline;
    }

    public static AirlineResponse toResponse(Airline req){
        return AirlineResponse.builder()
                .id(req.getId())
                .iataCode(req.getIataCode())
                .iacoCode(req.getIcaoCode())
                .name(req.getName())
                .alias(req.getAlias())
                .logoUrl(req.getLogoUrl())
                .website(req.getWebsite())
                .status(req.getStatus())
                .alliance(req.getAlliance())
                .support(req.getSupport())
                .createdAt(req.getCreatedAt())
                .updatedAt(req.getUpdatedAt())
                .ownerId(req.getOwnerId())
                .updatedById(req.getUpdatedById()!=null ? req.getUpdatedById() : 0L)
                .build();
    }

    public static void updateEntity(Airline airline, AirlineRequest req){
        if(airline == null || req == null)return;

        airline.setIataCode(req.getIataCode());
        airline.setIcaoCode(req.getIcaoCode());
        airline.setName(req.getName());
        airline.setAlias(req.getAlias());
        airline.setLogoUrl(req.getLogoUrl());
        airline.setWebsite(req.getWebsite());
        airline.setStatus(req.getStatus());
        airline.setAlliance(req.getAlliance());
        airline.setHeadquarterCityId(req.getHeadquarterCityId());

        if(airline.getSupport() == null){
            airline.setSupport(new Support());
        }

        airline.getSupport().setEmail(req.getSupportedEmail());
        airline.getSupport().setHours(req.getSupportedHours());
        airline.getSupport().setPhone(req.getSupportedPhone());
    }
}
