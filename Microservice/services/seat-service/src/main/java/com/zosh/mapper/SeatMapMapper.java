package com.zosh.mapper;

import com.zosh.model.CabinClass;
import com.zosh.model.Seat;
import com.zosh.model.SeatMap;
import com.zosh.payload.request.SeatMapRequest;
import com.zosh.payload.response.SeatMapResponse;
import com.zosh.payload.response.SeatResponse;

import java.util.List;
import java.util.stream.Collectors;

public class SeatMapMapper {
    public static SeatMap toSeatMap(
            SeatMapRequest request, CabinClass cabinClass){
        if (request == null || cabinClass == null)return null;

        return SeatMap.builder()
                .name(request.getName())
                .totalRows(request.getTotalRows())
                .leftSeatsPerRows(request.getLeftSeatPerRow())
                .rightSeatsPerRows(request.getRightSeatPerRow())
                .cabinClass(cabinClass )
                .build();
    }

    public static SeatMapResponse toResponse(SeatMap seatMap){
        if (seatMap == null)return null;

        List<Seat> seats = seatMap.getSeats();
        int totalSeats = seats!=null ? seats.size():0;
        int availableSeats = seats!=null ? (int) seats.stream().filter(seat->
        Boolean.TRUE.equals(seat.getIsAvailable()) &&
        Boolean.TRUE.equals(seat.getIsActive()) &&
        !Boolean.TRUE.equals(seat.getIsBlocked())).count() : 0;

        int windowSeats = seats!=null ? (int)seats.stream().filter(seat->
        seat.getSeatType().name().contains("WINDOW")).count():0;

        int aisleSeats = seats!=null ? (int)seats.stream().filter(seat->
                seat.getSeatType().name().contains("AISLE")).count():0;

        int middleSeats = seats!=null ? (int)seats.stream().filter(seat->
                seat.getSeatType().name().contains("MIDDLE")).count():0;

        int premiumSeats = seats!=null ? (int)seats.stream().filter(seat->
                Boolean.TRUE.equals(seat.getHasExtraLegroom()) ||
                        Boolean.TRUE.equals(seat.getIsEmergencyExit()) ||
                        Boolean.TRUE.equals(seat.getHasExtraWidth())).count() : 0;

        int emergencyExitSeats = seats!=null ? (int)seats.stream().filter(seat->
                Boolean.TRUE.equals(seat.getIsEmergencyExit())).count() : 0;

        return SeatMapResponse.builder()
                .id(seatMap.getId())
                .name(seatMap.getName())
                .totalRows(seatMap.getTotalRows())
                .leftSeatPerRow(seatMap.getLeftSeatsPerRows())
                .rightSeatPerRow(seatMap.getRightSeatsPerRows())
                .airlineId(seatMap.getAirlineId())
                .cabinClassId(seatMap.getCabinClass()!=null ? seatMap.getCabinClass().getId():null)
                .cabinClassName(seatMap.getCabinClass()!=null ? seatMap.getCabinClass().getName().toString():null)
                .cabinClassCode(seatMap.getCabinClass()!=null ? seatMap.getCabinClass().getCode():null)
                .totalSeats(totalSeats)
                .availableSeats(availableSeats)
                .occupiedSeats(totalSeats-availableSeats)
                .seats(seats!=null ? seats.stream().map(SeatMapper::toResponse)
                                     .collect(Collectors.toList()):null)
                .windowSeats(windowSeats)
                .aisleSeats(aisleSeats)
                .middleSeats(middleSeats)
                .emergencyExitSeats(emergencyExitSeats)
                .build();
    }

    public static void updateEntity(
            SeatMapRequest request,SeatMap existing){
        if (request == null)return;
        if(request.getName()!=null)existing.setName(request.getName());
        if(request.getTotalRows()!=null) existing.setTotalRows(request.getTotalRows());
        if(request.getLeftSeatPerRow()!=null)existing.setLeftSeatsPerRows(request.getLeftSeatPerRow());
        if(request.getRightSeatPerRow()!=null)existing.setRightSeatsPerRows(request.getRightSeatPerRow());
    }

    public static SeatMapResponse toSimpleResponse(SeatMap seatMap){
        return SeatMapResponse.builder()
                .totalSeats(seatMap.getTotalRows())
                .leftSeatPerRow(seatMap.getLeftSeatsPerRows())
                .rightSeatPerRow(seatMap.getRightSeatsPerRows())
                .build();
    }
}
