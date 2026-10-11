package com.zosh.event;

import com.zosh.client.BookingClient;
import com.zosh.enums.SeatAvailabilityStatus;
import com.zosh.payload.response.BookingResponse;
import com.zosh.payload.response.SeatInstanceResponse;
import com.zosh.service.SeatInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentEventListener {

    @Autowired
    private BookingClient bookingClient;

    @Autowired
    private SeatInstanceService seatInstanceService;

    @KafkaListener(topics = "payment.completed",groupId = "seat-service-group")
    public void handleBookingConfirmed(PaymentCompletedEvent event) {
        BookingResponse bookingResponse = bookingClient.getBookingById(event.getBookingId());

        List<SeatInstanceResponse> seatInstances = bookingResponse.getSeatInstances();

        for(SeatInstanceResponse seatInstanceResponse : seatInstances) {
            seatInstanceService.updateSeatInstanceStatus(seatInstanceResponse.getId(),
                    SeatAvailabilityStatus.BOOKED);
        }
    }
}
