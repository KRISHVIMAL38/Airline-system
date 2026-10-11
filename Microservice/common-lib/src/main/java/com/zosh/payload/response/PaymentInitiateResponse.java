package com.zosh.payload.response;

import com.zosh.enums.PaymentGateway;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInitiateResponse {
    private Long paymentId;
    private PaymentGateway gateway;
    private String transactionId;
    private String razorpayOrderId;
    private Double amount;
    private String currency;
    private String description;
    private String checkoutUrl;
    private String message;
    private Boolean success;
}
