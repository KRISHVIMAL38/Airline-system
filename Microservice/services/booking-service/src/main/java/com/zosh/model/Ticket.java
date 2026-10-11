package com.zosh.model;

import com.zosh.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Ticket{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ticketNumber;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private LocalDateTime issuedAt;

    @ManyToOne
    private Booking booking;

    @ManyToOne
    private Passenger passenger;
}
