package com.zosh.model;

import com.zosh.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class FlightInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long airlineId;

    @ManyToOne
    private Flight flight;

    @Column(nullable = false)
    private Long departureAirportId;

    @Column(nullable = false)
    private Long arrivalAirportId;

    @Column(nullable = false)
    private Long scheduledId;

    private LocalDateTime departureDateTime;

    private LocalDateTime arrivalDateTime;

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private Integer availableSeats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlightStatus status;

    private Integer minAdvancedBookingDays;
    private Integer maxAdvancedBookingDays;

    private Boolean isActive = true;

    @Transient
    public String getFormattedDuration(){
        if (departureDateTime == null || arrivalDateTime == null){
            return null;
        }

        Duration duration = Duration.between(departureDateTime,arrivalDateTime);
        long hours = duration.toHours();
        long mins = duration.toMinutes();

        StringBuilder sb = new StringBuilder();
        if(hours>0)sb.append(hours).append("h ");
        if (mins>0)sb.append(mins).append("min");

        return sb.toString().trim();
    }

}
