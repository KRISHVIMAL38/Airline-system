package com.zosh.payload.request;

import com.zosh.enums.AirlineStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirlineRequest {
    @NotBlank(message = "iata code is mandatory")
    @Size(min = 2,max = 2 , message = "IATA code must be of 2 digits")
    private String iataCode;

    @NotBlank(message = "icao code is mandatory")
    @Size(min = 3,max = 3 , message = "IACO code must be of 3 digits")
    private String icaoCode;

    @NotBlank(message = "Airline name is mandatory")
    private String name;

    private String alias;

    private String website;
    private  String logoUrl;

    private AirlineStatus status;
    private String alliance;

    private Long headquarterCityId;
    private String supportedEmail;
    private String supportedPhone;
    private String supportedHours;

}
