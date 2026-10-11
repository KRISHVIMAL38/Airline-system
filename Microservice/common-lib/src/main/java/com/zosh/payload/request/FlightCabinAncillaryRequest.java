package com.zosh.payload.request;

import com.zosh.payload.response.AncillaryResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FlightCabinAncillaryRequest {
    @NotNull(message = "Flight ID is required")
    private Long flightId;

    @NotNull(message = "CabinClass ID is required")
    private Long cabinClassId;

    @NotNull(message = "Ancillary ID is required")
    private Long ancillaryId;

    @NotNull(message = "Ancillary status is required")
    private Boolean available;

    private Integer maxQuantity;

    private Double price;

    @NotNull(message = "Included in fare status is required")
    private Boolean includedInFare;
}
