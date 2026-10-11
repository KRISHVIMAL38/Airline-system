package com.zosh.repository;

import com.zosh.enums.PaymentStatus;
import com.zosh.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByBookingId(Long bookingId);

    List<Payment> findByBookingIdIn(List<Long>bookingIds);
}
