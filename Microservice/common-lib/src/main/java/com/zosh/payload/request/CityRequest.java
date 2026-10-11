package com.zosh.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityRequest {
    @NotBlank(message = "city name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "city code is required")
    @Size(max = 10)
    private String cityCode;

    @NotBlank(message = "country name is required")
    @Size(max = 100)
    private String countryName;

    @NotBlank(message = "country code is required")
    @Size(max = 5)
    private String countryCode;

    @Size(max = 5)
    private String regionCode;

    @Size(max = 5)
    private String timeZoneOffset;
}
