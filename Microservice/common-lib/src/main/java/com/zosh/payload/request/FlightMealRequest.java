package com.zosh.payload.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FlightMealRequest {
    @NotNull(message = "Flight ID is required")
    private Long flightId;

    @NotNull(message = "Meal ID is required")
    private Long mealId;

    private Boolean available;
    @DecimalMin(value = "0.0",message = "Price can not be negative")
    private Double price;

    @Min(value = 0,message = "Display order can not negative")
    private Integer displayOrder;


}
