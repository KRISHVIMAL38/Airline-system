package com.zosh.service.impl;

import com.zosh.enums.TicketStatus;
import com.zosh.model.Booking;
import com.zosh.model.Passenger;
import com.zosh.model.Ticket;
import com.zosh.repository.TicketRepository;
import com.zosh.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Override
    public List<Ticket> generateTicketsForBooking(Booking booking) {
        List<Ticket> tickets = new ArrayList<>();

        for(Passenger passenger: booking.getPassengers()) {
            String ticketNumber  = generateUniqueTicketNumber();

            Ticket ticket = Ticket.builder()
                    .ticketNumber(ticketNumber)
                    .status(TicketStatus.BOOKED)
                    .issuedAt(LocalDateTime.now())
                    .passenger(passenger)
                    .booking(booking)
                    .build();

            Ticket savedTicket = ticketRepository.save(ticket);
            tickets.add(savedTicket);
        }

        return tickets;
    }

    private String generateUniqueTicketNumber() {
        String ticketNumber ;
        do {
            String datePart = LocalDateTime.now().toString().substring(0,10);
            String randomPart = UUID.randomUUID().toString().substring(0,8);

            ticketNumber = String .format("TKT-%s-%s", datePart, randomPart);
        }
        while(ticketRepository.existsByTicketNumber(ticketNumber));
        return ticketNumber;
    }
}
