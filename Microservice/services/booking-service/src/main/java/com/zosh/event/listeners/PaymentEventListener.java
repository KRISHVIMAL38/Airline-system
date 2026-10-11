package com.zosh.event.listeners;

import com.zosh.client.FlightClient;
import com.zosh.client.PricingClient;
import com.zosh.client.UserClient;
import com.zosh.dto.UserDTO;
import com.zosh.enums.BookingStatus;
import com.zosh.event.PaymentCompletedEvent;
import com.zosh.event.PaymentFailedEvent;
import com.zosh.event.publisher.BookingEventProducer;
import com.zosh.model.Booking;
import com.zosh.payload.response.FareResponse;
import com.zosh.payload.response.FlightInstanceResponse;
import com.zosh.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class PaymentEventListener {
    @Autowired
    private final BookingRepository bookingRepository;

    @Autowired
    private final FlightClient flightClient;

    @Autowired
    private final PricingClient pricingClient;

    @Autowired
    private final UserClient userClient;

    @Autowired
    private BookingEventProducer bookingEventProducer;

    @KafkaListener(topics = "payment.completed",groupId = "booking-service-group")
    public void handlePaymentCompleted(PaymentCompletedEvent paymentCompletedEvent) throws UnsupportedEncodingException {
        Booking booking = bookingRepository.findById(paymentCompletedEvent.getBookingId())
                .orElse(null);

        if(booking == null) return;

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        FlightInstanceResponse flightInstanceResponse = flightClient
                .getFlightInstanceById(booking.getFlightInstanceId());

        FareResponse fareResponse = pricingClient.getFairById(booking.getFareId());
        UserDTO userDTO = userClient.getUserById(booking.getUserId());

        // publish event for seat service
        // and notification service

        bookingEventProducer.sendBookingConfirmation(booking,
                paymentCompletedEvent,
                flightInstanceResponse,
                fareResponse,
                userDTO);
    }

    @KafkaListener(topics = "payment.failed" , groupId = "booking-service-group")
    public void handlePaymentFailed(PaymentFailedEvent paymentFailedEvent) throws Exception {
        Booking booking = bookingRepository.findById(paymentFailedEvent.getBookingId())
                .orElse(null);

        if(booking == null) return;

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}
