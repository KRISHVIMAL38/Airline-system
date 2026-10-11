package com.zosh.payload.request;

import com.zosh.enums.AircraftStatus;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.boot.availability.AvailabilityChangeEvent;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AircraftRequest {

    @NotBlank(message = "Aircraft code is required")
    private String code;

    @NotBlank(message = "Aircraft model is required")
    private String model;

    @NotBlank(message = "Aircraft manufacture is required")
    private String manufacture;
    //This is for integers . for Strings it is @NotBlank
    @NotNull(message = "seating capacity is required")
    @Positive(message = "seating capacity must be positive")
    private Integer seatingCapacity;

    @Positive(message = "economy seats must be required and must be positive")
    private  Integer economySeats;

    @Positive(message = " premium economy seats must be required and must be positive")
    private  Integer premiumEconomySeats;

    @Positive(message = "business seats must be required and must be positive")
    private  Integer businessSeats;

    @Positive(message = "first class seats must be required and must be positive")
    private  Integer firstClassSeats;

    @Positive(message = "Range must be positive")
    private Integer rangeKm;

    @Positive(message = "maximum altitude must be positive")
    private Integer maxAltitude;

    @Positive(message = "cruising speed must be positive")
    private Integer cruisingSpeedKmh;

    @Positive(message = "year of manufacture must be positive")
    private Integer yearOfManufacture;


    private LocalDate registrationDate;
    private LocalDate nextMaintenanceDate;

    @NotNull(message = "status is required")
    private AircraftStatus status;

    @NotNull(message = "availability status is required")
    private Boolean isAvailable;

    private Long currentAirportId;

}
