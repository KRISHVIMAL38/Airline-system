package com.zosh.event;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCompletedEvent {
    private Long paymentId;
    private Long bookingId;
    private Long userId;
    private Double amount;
    private String transactionId;
    private String providerPaymentId;
    private LocalDateTime paidAt;
}
