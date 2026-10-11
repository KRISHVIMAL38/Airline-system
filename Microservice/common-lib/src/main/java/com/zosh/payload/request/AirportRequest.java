package com.zosh.payload.request;

import com.zosh.embeddable.Address;
import com.zosh.embeddable.GeoCode;
import com.zosh.payload.response.CityResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirportRequest {
    @NotBlank(message = "IATA code is required")
    @Size(min = 3 ,max = 3,message = "IATA code should be exactly 3 characters")
    private String iataCode;

    @NotBlank(message = "Airport name is required")
    private String name;
    private String detailedName;
    private String timeZone;

    @Valid
    private Address address;

    @NotNull(message = "cityId can not be null")
    private Long cityId;

    @Valid
    private GeoCode geoCode;
}
