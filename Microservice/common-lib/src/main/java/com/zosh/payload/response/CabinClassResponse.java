package com.zosh.payload.response;

import com.zosh.enums.CabinClassType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CabinClassResponse {
    private Long id;
    private CabinClassType name;
    private String code;
    private String description;
    private Long aircraftId;
    private Integer displayOrder = 0;
    private Boolean isActive = true;
    private Boolean isBookable;
    private Integer typicalSeatPitch;
    private Integer typicalSeatWidth;
    private String seatType;
    private Instant createdAt;
    private Instant updatedAt;
    private SeatMapResponse seatMap;
}
