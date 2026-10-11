package com.zosh.controller;

import com.razorpay.RazorpayException;
import com.zosh.dto.PaymentDTO;
import com.zosh.payload.request.PaymentInitiateRequest;
import com.zosh.payload.request.PaymentVerifyRequest;
import com.zosh.payload.response.PaymentInitiateResponse;
import com.zosh.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<PaymentInitiateResponse> initiatePayment(
           @Valid @RequestBody PaymentInitiateRequest request) throws RazorpayException {
        PaymentInitiateResponse response = paymentService.initiatePayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(
            @Valid @RequestBody PaymentVerifyRequest request) throws RazorpayException {
        PaymentDTO response = paymentService.verifyPayment(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/batch/bookings")
    public ResponseEntity<Map<Long,PaymentDTO>> getPaymentsByBookingId(
            @RequestBody List<Long> bookingIds){
        return ResponseEntity.status(HttpStatus.OK).body(
                paymentService.getPaymentsByBookingIds(bookingIds));
    }

    @GetMapping
    public ResponseEntity<Page<PaymentDTO>> getAllPayments(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "20")int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestHeader("X-User-Id") Long userId){
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")?
                Sort.Direction.ASC:Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<PaymentDTO> payments = paymentService.getAllPayments(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(payments);
    }

}
