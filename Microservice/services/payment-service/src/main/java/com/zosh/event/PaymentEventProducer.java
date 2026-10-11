package com.zosh.event;

import com.zosh.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public void sendPaymentCompleted(Payment payment) {
            PaymentCompletedEvent paymentCompletedEvent = PaymentCompletedEvent.builder()
                    .paymentId(payment.getId())
                    .bookingId(payment.getBookingId())
                    .amount(payment.getAmount())
                    .userId(payment.getUserId())
                    .transactionId(payment.getTransactionId())
                    .providerPaymentId(payment.getProviderPaymentId())
                    .paidAt(payment.getPaidAt())
                    .build();

            kafkaTemplate.send("payment.completed", paymentCompletedEvent);
    }

    public void sendPaymentFailed(Payment payment) {
        PaymentFailedEvent event = PaymentFailedEvent.builder()
                .paymentId(payment.getId())
                .bookingId(payment.getBookingId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .transactionId(payment.getTransactionId())
                .failureReason(payment.getFailureReason())
                .failedAt(LocalDateTime.now())
                .build();

        kafkaTemplate.send("payment.failed", event);
    }
}
