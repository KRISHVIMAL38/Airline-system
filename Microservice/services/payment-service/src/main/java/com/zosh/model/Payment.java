package com.zosh.model;

import com.zosh.enums.PaymentGateway;
import com.zosh.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long bookingId;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentGateway provider;

    private String providerPaymentId;
    private String transactionId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String failureReason;
    private LocalDateTime paidAt;

    private String refundId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
