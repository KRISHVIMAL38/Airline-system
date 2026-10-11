package com.zosh.mapper;

import com.zosh.enums.SeatAvailabilityStatus;
import com.zosh.model.SeatInstance;
import com.zosh.payload.response.SeatInstanceResponse;

public class SeatInstanceMapper {
    public static SeatInstanceResponse toResponse(SeatInstance si) {
        if(si==null) return null;

        return SeatInstanceResponse.builder()
                .id(si.getId())
                .flightId(si.getFlightId())
                .seatId(si.getSeat()!=null ? si.getSeat().getId():null)
                .seatNumber(si.getSeat()!=null ? si.getSeat().getSeatNumber():null)
                .seatType(si.getSeat()!=null ? si.getSeat().getSeatType().toString():null)
                .seatPosition(si.getSeat()!=null ? si.getSeat().getFullPosition():null)
                .seat(SeatMapper.toResponse(si.getSeat()))
                .status(si.getStatus())
                .flightInstanceId(si.getFlightInstanceId())
                .flightCabinId(si.getFlightInstanceCabin()!=null ?
                        si.getFlightInstanceCabin().getId():null)
                .fare(si.getFare())
                .price(si.getPremiumSuperCharge())
                .version(si.getVersion())
                .createdAt(si.getCreatedAt())
                .updatedAt(si.getUpdatedAt())
                .isAvailable(si.isAvailable())
                .isBooked(si.isBooked())
                .isOccupied(si.getStatus() == SeatAvailabilityStatus.OCCUPIED)
                //.seatCharacteristics(
                   //     si.getSeat()!=null?si.getSeat().getCharacteristics():null)
                .build();
    }
}
