package com.zosh.service.impl;

import com.zosh.enums.SeatType;
import com.zosh.mapper.SeatMapper;
import com.zosh.model.Seat;
import com.zosh.model.SeatMap;
import com.zosh.payload.request.SeatRequest;
import com.zosh.payload.response.SeatResponse;
import com.zosh.repository.SeatMapRepository;
import com.zosh.repository.SeatRepository;
import com.zosh.service.SeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatMapRepository seatMapRepository;

    @Override
    public void generateSeats(Long seatMapId) throws Exception {
        boolean exists = seatRepository.existsBySeatMapId(seatMapId);
        if(exists){
            throw new Exception("seat already created for seat map");
        }

        SeatMap seatMap = seatMapRepository.findById(seatMapId).orElseThrow(()->
                new Exception("seat map not found with id  "+seatMapId));

        int leftSeatsPerRow = seatMap.getLeftSeatsPerRows();
        int rightSeatsPerRow = seatMap.getRightSeatsPerRows();
        int totalRows = seatMap.getTotalRows();
        int seatPerRow = leftSeatsPerRow+rightSeatsPerRow;

        List<Seat> seats = new ArrayList<>();

        for(int row = 1;row<=totalRows;row++){
            for(int col=0;col<seatPerRow;col++){
                String seatNum = row + getSeatLetter(col);
                SeatType seatType = getSeatType(col,leftSeatsPerRow,rightSeatsPerRow);

                Seat seat = Seat.builder()
                        .seatNumber(seatNum)
                        .seatRow(row)
                        .columnLetter(getSeatLetter(col).charAt(0))
                        .seatType(seatType)
                        .seatMap(seatMap)
                        .build();

                seats.add(seat);
            }
        }

        List<Seat> seats1 = seatRepository.saveAll(seats);

    }

    // 🚀 seat letter handling
    private String getSeatLetter(int col) {
        StringBuilder sb = new StringBuilder();
        while (col >= 0) {
            sb.insert(0, (char) ('A' + (col % 26)));
            col = col / 26 - 1;
        }
        return sb.toString();
    }

    private SeatType getSeatType(int seatIndex, int leftBlockSeats, int rightBlockSeats) {
        int totalSeats = leftBlockSeats + rightBlockSeats;

        // Windows
        if (seatIndex == 0 || seatIndex == totalSeats - 1) return SeatType.WINDOW;

        // Left aisle
        if (seatIndex == leftBlockSeats - 1) return SeatType.AISLE;

        // Right aisle
        if (seatIndex == leftBlockSeats) return SeatType.AISLE;

        // Everything else
        return SeatType.MIDDLE;
    }

    @Override
    public List<SeatResponse> getAll() {
        return seatRepository.findAll().stream().map(SeatMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SeatResponse updateSeats(Long seatId, SeatRequest request) {
        return null;
    }
}
