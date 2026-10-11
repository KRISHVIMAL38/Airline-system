package com.zosh.payload.response;

import com.zosh.embeddable.Address;
import com.zosh.embeddable.GeoCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirportResponse {
    private Long id;
    private String iataCode;
    private String name;
    private String detailedName;
    private String timeZone;
    private Address address;
    private CityResponse city;
    private GeoCode geoCode;
}
