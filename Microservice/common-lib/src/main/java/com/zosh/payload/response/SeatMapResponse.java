package com.zosh.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatMapResponse {
    private Long id;
    private String name;
    private Integer totalRows;

    private Long airlineId;
    private String airlineName;
    private String airlineCode;

    private Long cabinClassId;
    private String cabinClassName;
    private String cabinClassCode;

    private Integer totalSeats;
    private Integer availableSeats;
    private Integer occupiedSeats;

    private List<SeatResponse> seats;

    private Integer windowSeats;
    private Integer aisleSeats;
    private Integer middleSeats;
    private Integer emergencyExitSeats;

    private Integer leftSeatPerRow;
    private Integer rightSeatPerRow;

}
