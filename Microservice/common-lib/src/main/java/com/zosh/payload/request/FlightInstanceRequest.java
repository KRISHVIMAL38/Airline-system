package com.zosh.payload.request;

import com.zosh.enums.FlightStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightInstanceRequest {
    @NotNull(message = "Flight ID is required")
    private Long flightId;

    private Long airlineId;
    private Long scheduleId;
    private Long departureAirportId;
    private Long arrivalAirportId;

    @NotNull(message = "departure date-time is required")
    private LocalDateTime departureDateTime;
    @NotNull(message = "arrival date-time is required")
    private LocalDateTime arrivalDateTime;

    @Positive
    @NotNull(message = "total seats are required")
    private Integer totalSeats;

    @PositiveOrZero
    private Integer availableSeats;

    private FlightStatus status;

    private Integer minAdvanceBookingDays;
    private Integer maxAdvanceBookingDays;
    private Boolean isActive;



}
