package com.zosh.model;

import com.zosh.mapper.CityMapper;
import com.zosh.payload.request.AirportRequest;
import com.zosh.payload.response.AirportResponse;

public class AirportMapper {
    public static Airport toEntity(AirportRequest request){
        if(request==null)return null;

        return Airport.builder()
                .iataCode(request.getIataCode())
                .name(request.getName())
                //.timeZone(request.getTimeZone())
                .address(request.getAddress())
                .geoCode(request.getGeoCode())
                .build();
    }

    public static AirportResponse toResponse(Airport airport){
        if(airport == null)return null;

        return AirportResponse.builder()
                .id(airport.getId())
                .iataCode(airport.getIataCode())
                .name(airport.getName())
                .detailedName(airport.getDetailedName())
                //.timeZone(airport)
                .address(airport.getAddress())
                .city(CityMapper.toResponse(airport.getCity()))
                .geoCode(airport.getGeoCode())
                .build();
    }

    public static void updateEntity(AirportRequest request, Airport airportInDB){
        if(request==null || airportInDB == null)return ;

        if(request.getIataCode()!=null){
            airportInDB.setIataCode(request.getIataCode());
        }

        if (request.getName()!=null){
            airportInDB.setName(request.getName());
        }

        if(request.getTimeZone()!=null){
            airportInDB.setTimeZone(request.getTimeZone());
        }

        if(request.getAddress()!=null){
            airportInDB.setAddress(request.getAddress());
        }

        if(request.getGeoCode()!=null){
            airportInDB.setGeoCode(request.getGeoCode());
        }
    }
}
