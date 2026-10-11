package com.zosh.service.impl;

import com.zosh.client.*;
import com.zosh.dto.PaymentDTO;
import com.zosh.enums.BookingStatus;
import com.zosh.enums.PaymentGateway;
import com.zosh.mapper.BookingMapper;
import com.zosh.model.Booking;
import com.zosh.model.Passenger;
import com.zosh.payload.request.BookingRequest;
import com.zosh.payload.request.PassengerRequest;
import com.zosh.payload.request.PaymentInitiateRequest;
import com.zosh.payload.response.*;
import com.zosh.repository.BookingRepository;
import com.zosh.service.BookingService;
import com.zosh.service.PassengerService;
import com.zosh.service.TicketService;
import com.zosh.service.integration.FareIntegrationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    PassengerService passengerService;
    @Autowired
    TicketService ticketService;
    @Autowired
    FlightClient flightClient;
    @Autowired
    SeatClient seatClient;
    @Autowired
    AncillaryClient ancillaryClient;
    @Autowired
    FareIntegrationService fareIntegrationService;
    @Autowired
    PaymentClient paymentClient;
    @Autowired
    AirlineClient airlineClient;

    @Override
    public PaymentInitiateResponse createBooking(BookingRequest request, Long userId) {
        // step1 create unique booking reference
        String bookingReference = generateBookingReference();

        //create passenger

        Set<Passenger> passengers = new HashSet<>();

        //step 2
        for(PassengerRequest passengerRequest : request.getPassengers()){
            Passenger passenger = passengerService.createPassenger(passengerRequest,userId);
            passengers.add(passenger);
        }

        // Step 3 check if flight exists or not
        FlightResponse flightResponse = flightClient.getFlightById(request.getFlightId());

        //Step 4
        //Create booking with pending status
        Booking booking = BookingMapper.toEntity(request,userId,passengers,bookingReference);

        // set airline id from flightResponse
        booking.setAirlineId(flightResponse.getAirline().getId());

        //Step 5 Set seat Instance ids
        List<Long> seatInstanceIds = request.getPassengers().stream()
                .map(PassengerRequest::getSeatInstanceId)
                .toList();

        booking.setSeatInstanceIds(seatInstanceIds);
        Booking savedBooking = bookingRepository.save(booking);

        //set booking reference on passengers
        for(Passenger passenger : passengers) {
            passenger.setBooking(savedBooking);
        }

        //Step 6 generate Ticket for each passenger
        ticketService.generateTicketsForBooking(booking);

        //Step 7
        // calculateThePrice
        //1.calculate fare total
        Double fareTotal = fareIntegrationService.calculateFareTotal(request.getFareId());

        //2. seat price
        Double seatPrice = seatClient.getSeatPrice(booking.getSeatInstanceIds());
        //3. Ancillary price
        Double ancillaryPrice = ancillaryClient.calculateAncillariesPrice(booking.getSeatInstanceIds());
        //4. meal price

        Double mealPrice = ancillaryClient.calculateMealPrice(booking.getMealIds());

        Double totalPrice = mealPrice + fareTotal + seatPrice + ancillaryPrice;

        System.out.println("User Id: " + userId+" booking id "+booking.getId()
                            +" gateway = "+PaymentGateway.RAZORPAY+" booking reference = "+bookingReference);
        // Initiate Payment using paymentService
        PaymentInitiateRequest initiateRequest = PaymentInitiateRequest
                .builder()
                .userId(userId)
                .bookingId(booking.getId())
                .amount(totalPrice)
                .gateway(PaymentGateway.RAZORPAY)
                .description("payment for booking id "+bookingReference)
                .build();
        return paymentClient
                .initiatePayment(initiateRequest);

    }

    @Override
    public BookingResponse updateBooking(Long id, BookingRequest request) {
        return null;
    }

    @Override
    public BookingResponse getBookingById(Long id) throws EntityNotFoundException {
        Booking booking = bookingRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Booking not found with id " + id));

        return convertToResponse(booking);
    }

    @Override
    public List<BookingResponse> getAllBookingsByAirlineId(Long userId, String searchQuery, BookingStatus status, Long flightInstanceId, String sortDirection) {
        AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);

        Sort.Direction direction = "asc".equalsIgnoreCase(sortDirection)
                ?Sort.Direction.ASC
                :Sort.Direction.DESC;

        Sort sort = Sort.by(direction, "bookingDate");
        List<Booking> bookingList = bookingRepository.findByAirlineWIthFilter(airlineResponse.getId(),
                searchQuery,status,flightInstanceId,sort);

        return bookingList.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Booking not found with id " + id));
        bookingRepository.delete(booking);
    }

    @Override
    public List<BookingResponse> getAllBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream().map(this::convertToResponse).toList();

    }

    @Override
    public BookingResponse cancelBookings(Long bookingId) throws EntityNotFoundException {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()->
                new EntityNotFoundException("Booking not found with id " + bookingId));

        booking.setStatus(BookingStatus.CANCELLED);
        Booking savedBooking  = bookingRepository.save(booking);

        return convertToResponse(savedBooking);
    }

    private String generateBookingReference(){
        String reference; ;

        do {
            reference = "BK"+ UUID.randomUUID().toString()
                    .substring(0,8).toUpperCase();
        }
        while (bookingRepository.existsByBookingReference(reference));
        return reference;
    }

    private BookingResponse convertToResponse(Booking booking) {
        //todo : when enable feign client use actual responses

        List<FlightCabinAncillaryResponse> ancillaryResponses = new ArrayList<>();
        List<FlightMealResponse> mealResponses = new ArrayList<>();
        PaymentDTO paymentDTO = new PaymentDTO();
        FareResponse fareResponse = new FareResponse();
        FlightResponse flightResponse = new FlightResponse();
        List<SeatInstanceResponse> seatInstanceResponses = new ArrayList<>();
        FlightInstanceResponse flightInstanceResponse = new FlightInstanceResponse();

        return BookingMapper.toResponse(
                booking,
                paymentDTO,
                fareResponse,
                flightResponse,
                flightInstanceResponse,
                ancillaryResponses,
                mealResponses,
                seatInstanceResponses
        );
    }

}
