package com.zosh.model;

import com.zosh.enums.AircraftStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String code;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false,length = 50)
    private String manufacture;

    @Column(nullable = false)
    private Integer seatingCapacity;

    @Column(name = "economy_seats")
    private Integer economySeats = 0;

    @Column(name = "premium_economy_seats")
    private Integer premiumEconomySeats = 0;

    @Column(name = "business_seats")
    private Integer businessSeats = 0;

    @Column(name = "first_class_seats")
    private Integer firstClassSeats = 0;

    @Column(name = "cruising_speed_kmh")
    private Integer cruisingSpeedKmh;

    private Integer rangeKm;
    private Integer maxAltitude;

    @Column(name = "year_of_manufacture")
    private Integer yearOfManufacture;

    private LocalDate registrationDate;

    private LocalDate nextMaintenanceDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false,length = 20)
    private AircraftStatus status = AircraftStatus.ACTIVE;

    private Boolean isAvailable = true;
    private Boolean requiresMaintenance;

    @ManyToOne
    private Airline airline;

    private Long currenAirportId;

    @CreatedDate
    @Column(name = "created_at",nullable = false,updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;

    public Integer getTotalSeats(){
        return economySeats+premiumEconomySeats
                +firstClassSeats+businessSeats;
    }

    public boolean isOperational(){
        return AircraftStatus.ACTIVE.equals(status)
                && Boolean.TRUE.equals(isAvailable);
    }

    private boolean requiresMaintenance(){
        return nextMaintenanceDate!=null
                && nextMaintenanceDate.isBefore(LocalDate.now().plusWeeks(2));
    }
}
