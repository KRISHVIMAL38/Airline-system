package com.zosh.model;

import com.zosh.enums.SeatAvailabilityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class SeatInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long flightId;

    @ManyToOne
    private FlightInstanceCabin flightInstanceCabin;

    private Long flightInstanceId;

    @ManyToOne
    private Seat seat;

    private SeatAvailabilityStatus status = SeatAvailabilityStatus.AVAILABLE;

    private boolean isBooked = false;
    private boolean isAvailable = true;

    private Double Fare;
    private Double premiumSuperCharge;

    private Long flightScheduleId;

    @Version
    private Long version;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

}
