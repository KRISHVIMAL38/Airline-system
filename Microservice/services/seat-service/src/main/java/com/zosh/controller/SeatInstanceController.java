package com.zosh.controller;

import com.zosh.model.SeatInstance;
import com.zosh.service.SeatInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seat-instances")
public class SeatInstanceController {
    @Autowired
    private SeatInstanceService seatInstanceService;

    @PostMapping("/price/total")
    public Double getSeatPrice(@RequestBody List<Long> seatInstanceIds) {
        return seatInstanceService.calculateTotalSeatPrice(seatInstanceIds);
    }
}
