package com.zosh.payload.response;

import com.zosh.enums.FlightStatus;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightResponse {
    private long id;
    private String flightNumber;
    private AirlineResponse airline;
    private AircraftResponse aircraft;
    private AirportResponse departureAirport;
    private AirportResponse arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private FlightStatus status;
    private Double lowestPrice;
    private Integer totalAvailableSeats;
    private Instant createdAt;
    private Instant updatedAt;

}
