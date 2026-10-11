package com.zosh.mapper;

import com.zosh.model.City;
import com.zosh.payload.request.CityRequest;
import com.zosh.payload.response.CityResponse;

public class CityMapper {
    public static City toEntity(CityRequest request){
        if(request==null)return null;

        return City.builder()
                .name(request.getName())
                .cityCode(request.getCityCode())
                .countryCode(request.getCountryCode())
                .countryName(request.getCountryName())
                .regionCode(request.getRegionCode())
                .timeZoneId(request.getTimeZoneOffset())
                .build();
    }

    public static CityResponse toResponse(City city){
        if(city == null) return  null;

        return CityResponse.builder()
                .Id(city.getId())
                .name(city.getName())
                .cityCode(city.getCityCode())
                .countryCode(city.getCountryCode())
                .countryName(city.getCountryName())
                .regionCode(city.getRegionCode())
                .timeZoneOffset(city.getTimeZoneId())
                .build();
    }

    public static City updateEntiity(City city,CityRequest request){
        if(request.getName()!=null){
            city.setName(request.getName().trim());
        }
        if(request.getCityCode() != null){
            city.setCityCode(request.getCityCode().trim());
        }
        if(request.getCountryName()!=null){
            city.setCountryName(request.getCountryName().trim());
        }
        if(request.getCountryCode() != null){
            city.setCityCode(request.getCountryCode().trim());
        }
        if(request.getTimeZoneOffset()!=null){
            city.setTimeZoneId(request.getTimeZoneOffset().trim());
        }

        return city;
    }






}
