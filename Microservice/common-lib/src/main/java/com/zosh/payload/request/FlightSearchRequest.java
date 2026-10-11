package com.zosh.payload.request;

import com.zosh.enums.CabinClassType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightSearchRequest {
    private Long departureAirportId;
    private Long arrivalAirportId;
    @NotNull(message = "departure date is required")
    private LocalDate departureDate;

    @Min(value = 1,message = "At least one passenger is required")
    private Integer passengers;

    @NotNull(message = "cabin class is required")
    private CabinClassType cabinClass;

    //filter parameter . This is optional
    private List<Long> airlines;
    private Double minPrice;
    private Double maxPrice;

    private String departureTimeRange;
    private String arrivalTimeRange;
    private LocalDate arrivalDate;

    private Integer maxDuration;
    private String sortBy;
    private String sortOrder;
}
