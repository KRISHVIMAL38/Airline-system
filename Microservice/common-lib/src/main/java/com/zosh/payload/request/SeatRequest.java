package com.zosh.payload.request;

import com.zosh.enums.SeatType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatRequest {
    @NotBlank(message = "seat number is required")
    private String seatNumber;

    @NotBlank(message = "seat row is required")
    private Integer seatRow;

    private Character columnLetter;

    @NotNull(message = "seat type is required")
    private SeatType seatType;

    @NotNull(message = "seat map id is required")
    private Long seatMapId;

    private Long cabinClassId;

    private Boolean isAvailable = true;
    private Boolean isBlocked =false;
    private Boolean isEmergencyExit =false;
    private Boolean isActive = true;

    private Double basePrice;
    private Double premiumSuperCharge;

    private Boolean hasExtraLegroom = false;
    private Boolean hasPowerOutlet = false;
    private Boolean hasTvScreen =false;

    private Boolean hasExtraWidth = false;

    private Integer seatPitch;
    private Integer seatWidth;

}
