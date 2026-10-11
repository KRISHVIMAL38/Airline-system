package com.zosh.mapper;

import com.zosh.dto.PaymentDTO;
import com.zosh.enums.BookingStatus;
import com.zosh.model.Booking;
import com.zosh.model.Passenger;
import com.zosh.payload.request.BookingRequest;
import com.zosh.payload.response.*;

import java.util.List;
import java.util.Set;

public class BookingMapper {
    public static Booking toEntity(BookingRequest request,
                                   Long userId,
                                   Set<Passenger> passengers,
                                   String bookingReference) {
        return Booking.builder()
                .bookingReference(bookingReference)
                .userId(userId)
                .flightId(request.getFlightId())
                .flightInstanceId(request.getFlightInstanceId())
                .fareId(request.getFareId())
                .contactInfo(request.getContactInfo())
                .passengers(passengers)
                .cabinClass(request.getCabinClass())
                .ancillaryIds(request.getAncillaryIds())
                .mealIds(request.getMealIds())
                .status(BookingStatus.PENDING)
                .build();
    }

    public static BookingResponse toResponse(Booking booking,
                                             PaymentDTO paymentDTO,
                                             FareResponse fareResponse,
                                             FlightResponse flightResponse,
                                             FlightInstanceResponse flightInstanceResponse,
                                             List<FlightCabinAncillaryResponse> ancillaries,
                                             List<FlightMealResponse> meals,
                                             List<SeatInstanceResponse> seats) {
        if (booking == null) return null;

        List<PassengerResponse> passengerResponses = booking.getPassengers()!=null ?
                booking.getPassengers().stream().map(PassengerMapper::toResponse)
                .toList() : null;

        List<TicketResponse> ticketResponses = booking.getTickets()!=null?
                booking.getTickets().stream().map(TicketMapper::toResponse)
                .toList():null;


        return BookingResponse.builder()
                .id(booking.getId())
                .bookingReference(booking.getBookingReference())
                .userId(booking.getUserId())
                .flightId(booking.getFlightId())
                .flightNumber(flightResponse!=null?flightResponse.getFlightNumber():null)
                .flightName(flightResponse!=null && flightResponse.getArrivalAirport()!=null? flightResponse.getArrivalAirport().getCity().getName():null)
                .departureTime(flightInstanceResponse!=null?flightInstanceResponse.getDepartureDateTime():null)
                .arrivalTime(flightInstanceResponse!=null?flightInstanceResponse.getArrivalDateTime():null)
                .flightDuration(flightInstanceResponse!=null? flightInstanceResponse.getFormattedDuration():null)
                .departureAirport(flightResponse!=null?flightResponse.getDepartureAirport()!=null?flightResponse.getDepartureAirport().getName():null:null)
                .arrivalAirport(flightResponse!=null?flightResponse.getArrivalAirport()!=null?flightResponse.getArrivalAirport().getName():null:null)
                .status(booking.getStatus())
                .bookingDate(booking.getBookingDate())
                .lastModified(booking.getLastModified())
                .passengers(passengerResponses)
                .tickets(ticketResponses)
                .fare(fareResponse)
                .totalPassengers(passengerResponses!=null?passengerResponses.size():0)
                .ancillaries(ancillaries)
                .meals(meals)
                .seatInstances(seats)
                .paymentStatus(paymentDTO!=null? paymentDTO.getStatus():null)

                .fareName(fareResponse !=null ? fareResponse.getName():null)
                .fareBaseFare(fareResponse!=null? fareResponse.getBaseFare():null)
                .fareTaxesAndFees(fareResponse!=null? fareResponse.getTaxesAndFees():null)
                .fareAirlineFees(fareResponse!=null? fareResponse.getAirlineFees():null)

                .totalAmounts(fareResponse!=null?fareResponse.getTotalPrice():null)
                .contactInfo(booking.getContactInfo())
                .build();
    }
}
