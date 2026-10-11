package com.zosh.service;

import com.zosh.enums.BookingStatus;
import com.zosh.payload.request.BookingRequest;
import com.zosh.payload.response.BookingResponse;
import com.zosh.payload.response.PaymentInitiateResponse;

import java.util.List;

public interface BookingService {
    PaymentInitiateResponse createBooking(BookingRequest request, Long userId);
    BookingResponse updateBooking(Long id,BookingRequest request);
    BookingResponse getBookingById(Long id);
    List<BookingResponse> getAllBookingsByAirlineId(Long userIdId,
                                                    String searchQuery,
                                                    BookingStatus status,
                                                    Long flightInstanceId,
                                                    String sortDirection);
    void deleteBooking(Long id);
    List<BookingResponse> getAllBookingsByUserId(Long userId);

    BookingResponse cancelBookings(Long bookingId);


}
