package com.zosh.service;

import com.zosh.event.BookingConfirmedEvent;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class BookingNotificationListener {
    private final EmailService emailService;

    @KafkaListener(topics = "booking.confirmed",
            groupId = "notification-service-group")
    @Transactional
    public void handleBookingConfirmedEvent(@Payload
                                            BookingConfirmedEvent bookingConfirmedEvent) throws MessagingException, UnsupportedEncodingException {
        System.out.println("Received BookingConfirmedEvent");
        emailService.sendBookingConfirmation(bookingConfirmedEvent);
    }
}
