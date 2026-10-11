package com.zosh.controller;

import com.zosh.payload.request.SeatMapRequest;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.SeatMapResponse;
import com.zosh.service.SeatMapService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seat-maps")
public class SeatMapController {
    @Autowired
    private SeatMapService seatMapService;

    @PostMapping
    public ResponseEntity<SeatMapResponse> createSeatMap(
            @Valid @RequestBody SeatMapRequest request,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(seatMapService.creatSeatMap(userId,request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatMapResponse> getSeatMapById(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(seatMapService.getSeatMapById(id));
    }

    @GetMapping("/cabin-class/{cabinClassId}")
    public ResponseEntity<SeatMapResponse> getSeatMapByCabinClassId(@PathVariable Long cabinClassId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(seatMapService.getSeatMapByCabinClassId(cabinClassId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatMapResponse> updateSeatMap(
            @PathVariable Long id, @RequestBody SeatMapRequest request
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).
                body(seatMapService.updateSeatMap(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSeatMap(@PathVariable Long id) throws Exception {
        seatMapService.deleteSeatMap(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse("seatMap deleted successfully"));
    }

}
