package com.zosh.service.impl;

import com.razorpay.RazorpayException;
import com.zosh.client.UserClient;
import com.zosh.dto.PaymentDTO;
import com.zosh.dto.UserDTO;
import com.zosh.enums.PaymentGateway;
import com.zosh.enums.PaymentStatus;
import com.zosh.event.PaymentEventProducer;
import com.zosh.mapper.PaymentMapper;
import com.zosh.model.Payment;
import com.zosh.payload.request.PaymentInitiateRequest;
import com.zosh.payload.request.PaymentVerifyRequest;
import com.zosh.payload.response.PaymentInitiateResponse;
import com.zosh.payload.response.PaymentLinkResponse;
import com.zosh.repository.PaymentRepository;
import com.zosh.service.PaymentService;
import com.zosh.service.gateway.RazorPayService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private RazorPayService razorPayService;

    @Autowired
    private PaymentEventProducer  paymentEventProducer;

    @Autowired
    private UserClient userClient;

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws RazorpayException {
        System.out.println("Inside initiatePayment");
        paymentRepository.findByBookingId(request.getBookingId())
                .ifPresent(payment->{
                    if(payment.getStatus() == PaymentStatus.SUCCESS){
                        throw new RuntimeException("payment already completed for this booking");
                    }
                });

        Payment payment = Payment.builder()
                .userId(request.getUserId())
                .bookingId(request.getBookingId())
                .amount(request.getAmount())
                .provider(request.getGateway())
                .status(PaymentStatus.PENDING)
                .transactionId(generateTransactionId())
                .build();
        System.out.println("payment details are "+payment.toString());
        payment = paymentRepository.save(payment);

        PaymentInitiateResponse response = PaymentInitiateResponse.builder()
                .paymentId(payment.getId())
                .gateway(request.getGateway())
                .transactionId(payment.getTransactionId())
                .amount(request.getAmount())
                .description(request.getDescription())
                .success(true)
                .message("payment initiated successfully")
                .build();

        if(request.getGateway() == PaymentGateway.RAZORPAY){
            // fetch UserDetails using feign client

            UserDTO userDTO = userClient.getUserById(request.getUserId());

            // create RazorPay payment link using razorpay  service

            PaymentLinkResponse paymentLinkResponse = razorPayService.createPaymentLink(userDTO,payment);
            //Set payment link to response
            response.setRazorpayOrderId(paymentLinkResponse.getPayment_link_id());
            response.setCheckoutUrl(paymentLinkResponse.getPayment_link_url());
        }
        return response;
    }

    private String generateTransactionId() {
        return "TXN_"+System.currentTimeMillis()+"_" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }

    @Override
    public PaymentDTO verifyPayment(PaymentVerifyRequest request) throws RazorpayException {
        JSONObject paymentDetails = razorPayService.
                fetchPaymentDetails(request.getRazorPaymentId());

        String status = paymentDetails.optString("status");
        JSONObject notes  = paymentDetails.optJSONObject("notes");
        Long paymentId = Long.parseLong(paymentDetails.optString("payment_id"));

        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()->
                new RuntimeException("payment not found"));

        boolean isValid = "captured".equalsIgnoreCase(status);
        if(isValid){
            if(payment.getProvider() == PaymentGateway.RAZORPAY){
                payment.setProviderPaymentId(request.getRazorPaymentId());
            }
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setPaidAt(LocalDateTime.now());
            paymentRepository.save(payment);

            //publish payment success kafka event
            paymentEventProducer.sendPaymentCompleted(payment);
        }
        else{
            payment.setStatus(PaymentStatus.FAILED);
            payment.setFailureReason("Payment verification failed");
            paymentRepository.save(payment);

            //publish payment failed kafka event
            paymentEventProducer.sendPaymentFailed(payment);
        }

        return PaymentMapper.toPaymentDTO(payment);
    }

    @Override
    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable)
                .map(PaymentMapper::toPaymentDTO);
    }

    @Override
    public Map<Long, PaymentDTO> getPaymentsByBookingIds(List<Long> bookingIds) {
        return paymentRepository.findByBookingIdIn(bookingIds)
                .stream().collect(Collectors.toMap(Payment::getId,PaymentMapper::toPaymentDTO));
    }
}
