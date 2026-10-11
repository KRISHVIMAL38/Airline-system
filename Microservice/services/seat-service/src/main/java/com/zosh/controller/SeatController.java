package com.zosh.controller;

import com.zosh.payload.response.SeatResponse;
import com.zosh.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @GetMapping
    public ResponseEntity<List<SeatResponse>> getAllSeats(){
        return ResponseEntity.status(HttpStatus.OK).body(seatService.getAll());
    }

}
