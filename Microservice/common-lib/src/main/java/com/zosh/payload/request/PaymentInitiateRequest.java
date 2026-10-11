package com.zosh.payload.request;

import com.zosh.enums.PaymentGateway;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInitiateRequest {
    @NotNull(message = "user id is required")
    private Long userId;

    @NotNull(message = "booking id is required")
    private Long bookingId;

    @NotNull(message = "Payment gateway is required")
    private PaymentGateway gateway;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;

    private String description;


}
