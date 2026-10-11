package com.zosh.payload.response;

import com.zosh.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightInstanceResponse {
    private Long id;
    private Long flightId;
    private String flightNumber;
    private Long airlineId;
    private String airlineName;
    private String airlineLogo;
    private Long aircraftId;
    private String aircraftModel;
    private String aircraftCode;
    private AirportResponse departureAirport;
    private AirportResponse arrivalAirport;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String formattedDuration;

    private Integer totalSeats;
    private Integer availableSeats;
    private FlightStatus status;

    private Integer minAdvanceBookingDays;
    private Integer maxAdvanceBookingDays;
    private Boolean isActive;

    private Long version;
    private FareResponse fare;

}
