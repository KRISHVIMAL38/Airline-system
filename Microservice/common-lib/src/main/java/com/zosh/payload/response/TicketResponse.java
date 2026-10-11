package com.zosh.payload.response;

import com.zosh.enums.TicketStatus;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponse {
    private Long id;
    private String ticketNumber;
    private TicketStatus status;
    private LocalDateTime issuedAt;

    //Booking Details
    private Long bookingId;
    private String bookingReference;

    //passenger details
    private Long passengerId;
    private String passengerFirstName;
    private String passengerLastName;
    private String passengerEmail;

    //payment Details
    private Long paymentId;
    private Double paymentAmount;

}
