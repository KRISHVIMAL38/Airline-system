package com.zosh.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentFailedEvent {
    Long paymentId;
    Long bookingId;
    Long userId;
    Double amount;
    String transactionId;
    String failureReason;
    LocalDateTime failedAt;
}
