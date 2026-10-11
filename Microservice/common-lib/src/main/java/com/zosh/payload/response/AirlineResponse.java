package com.zosh.payload.response;

import com.zosh.dto.UserDTO;
import com.zosh.embeddable.Support;
import com.zosh.enums.AirlineStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirlineResponse {
    private Long id;
    private String iataCode;
    private String iacoCode;
    private String name;
    private String alias;
    private String logoUrl;
    private String website;
    private AirlineStatus status;
    private String alliance;
    private Instant createdAt;
    private Instant updatedAt;

    private Long ownerId;
    private UserDTO owner;
    private long updatedById;

    private CityResponse headquarterCity;
    private Support support;


}
