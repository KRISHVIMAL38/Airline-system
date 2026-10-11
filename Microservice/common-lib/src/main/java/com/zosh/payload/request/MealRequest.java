package com.zosh.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealRequest {
    @NotBlank(message = "meal code is required")
    private String code;

    @NotBlank(message = "meal name is required")
    private String name;

    @NotBlank(message = "meal type is required")
    @Size(max = 50 ,message = "meal type must not exceed 50 characters")
    private String mealType;

    @Size(max = 100 ,message = "dietaryRestriction must not exceed 100 characters")
    private String dietaryRestriction;

    @Size(max = 2000 ,message = "ingredients must not exceed 2000 characters")
    private String ingredients;

    @Size(max = 500 ,message = "imageUrl must not exceed 500 characters")
    private String imageUrl;

    private Boolean requiredAdvanceBooking;
    private Integer advanceBookingHours;
    private Integer displayOrder;
    private Long airlineId;
    private Instant createdAt;
    private Instant updatedAt;
}
