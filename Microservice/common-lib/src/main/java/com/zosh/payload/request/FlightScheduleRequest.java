package com.zosh.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightScheduleRequest {
    @NotNull(message = "Flight ID is required")
    private Long flightId;

    @NotNull(message = "departure time is required")
    private LocalTime departureTime;

    @NotNull(message = "arrival time is required")
    private LocalTime arrivalTime;

    @NotNull(message = "start date is required")
    private LocalDate startDate;

    @NotNull(message = "end date is required")
    private LocalDate endDate;

    private List<DayOfWeek> operatingDays;

    private Boolean isActive;
}
