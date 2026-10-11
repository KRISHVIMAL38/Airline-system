package com.zosh.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatMapRequest {
    @NotBlank(message = "seat map name is required")
    private String name;

    @Positive
    @NotNull(message = "total row is required")
    private Integer totalRows;

    @Positive
    @NotNull(message = "left seat per row is required")
    private Integer leftSeatPerRow;

    @Positive
    @NotNull(message = "right seat row is required")
    private Integer rightSeatPerRow;

    private Long cabinClassId;

}
