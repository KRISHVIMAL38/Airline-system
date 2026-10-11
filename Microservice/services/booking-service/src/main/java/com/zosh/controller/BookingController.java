package com.zosh.controller;

import com.zosh.enums.BookingStatus;
import com.zosh.payload.request.BookingRequest;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.BookingResponse;
import com.zosh.payload.response.PaymentInitiateResponse;
import com.zosh.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<PaymentInitiateResponse> createBooking(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                bookingService.createBooking(bookingRequest, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.getBookingById(id));
    }

    @GetMapping("/airline")
    public ResponseEntity<List<BookingResponse>> getBookingByAirline(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false)BookingStatus bookingStatus,
            @RequestParam(required = false) Long flightInstanceId,
            @RequestParam(defaultValue = "DESC")String sortDirection) {

        List<BookingResponse> bookingResponseList = bookingService.getAllBookingsByAirlineId(
                userId, search, bookingStatus, flightInstanceId, sortDirection
        );
        return ResponseEntity.status(HttpStatus.OK).body(bookingResponseList);
    }

    @GetMapping("/user/history")
    public ResponseEntity<List<BookingResponse>> getBookingByUserId(
            @RequestHeader("X-User-Id") Long userId){
        List<BookingResponse> bookingResponseList = bookingService.getAllBookingsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(bookingResponseList);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                bookingService.cancelBookings(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBooking(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId){
        bookingService.deleteBooking(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse("Booking deleted successfully with id " + id));
    }
}
