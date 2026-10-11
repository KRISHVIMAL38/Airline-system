package com.zosh.service;

import com.razorpay.RazorpayException;
import com.zosh.dto.PaymentDTO;
import com.zosh.payload.request.PaymentInitiateRequest;
import com.zosh.payload.request.PaymentVerifyRequest;
import com.zosh.payload.response.PaymentInitiateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws RazorpayException;
    PaymentDTO verifyPayment(PaymentVerifyRequest request) throws RazorpayException;
    Page<PaymentDTO> getAllPayments(Pageable pageable);
    Map<Long,PaymentDTO> getPaymentsByBookingIds(List<Long> bookingIds);
}
