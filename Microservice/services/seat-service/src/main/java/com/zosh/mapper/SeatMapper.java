package com.zosh.mapper;

import com.zosh.model.Seat;
import com.zosh.payload.response.SeatResponse;

public class SeatMapper {
    public static SeatResponse toResponse(Seat request){
        if(request == null)return null;

        return SeatResponse.builder()
                .id(request.getId())
                .seatNumber(request.getSeatNumber())
                .seatRow(request.getSeatRow())
                .columnLetter(request.getColumnLetter())
                .seatType(request.getSeatType())
                .isAvailable(request.getIsAvailable())
                .isBlocked(request.getIsBlocked())

                .isActive(request.getIsActive())
                .basePrice(request.getBasePrice())
                .premiumSuperCharge(request.getPremiumSuperCharge())
                .totalPrice(request.getTotalPrice())
                .hasExtraLegroom(request.getHasExtraLegroom())


                .hasPowerOutlet(request.getHasPowerOutlet())
                .hasTvScreen(request.getHasTvScreen())

                .hasExtraWidth(request.getHasExtraWidth())
                .seatPitch(request.getSeatPitch())
                .seatWidth(request.getSeatWidth())

                .seatMapId(request.getSeatMap()!=null ? request.getSeatMap().getId():null)
                .seatMapName(request.getSeatMap()!=null ? request.getSeatMap().getName():null)
                .cabinClassId(request.getCabinClass()!=null ? request.getCabinClass().getId() : null)
                .cabinClassName(request.getCabinClass()!=null ? request.getCabinClass().getName().toString() : null)

                .createdAt(request.getCreatedAt())
                .createdBy(request.getCreatedBy())
                .updatedAt(request.getUpdatedAt())
                .updatedBy(request.getUpdatedBy())

                //.isBookable(request.isBookable())
                .fullPosition(request.getFullPosition())
                .build();
    }
}
