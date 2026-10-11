package com.zosh.service;

import com.zosh.event.BookingConfirmedEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class EmailService {
    private JavaMailSender mailSender;
    private TemplateEngine templateEngine;

    @Value("${notification.from-email}")
    private String fromEmail;

    @Value("${notification.from-name}")
    private String fromName;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter
            .ofPattern("dd MMM yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter
            .ofPattern("HH:mm", Locale.ENGLISH);
    private static final DateTimeFormatter DT_FMT = DateTimeFormatter
            .ofPattern("dd MMM yyyy, HH:mm", Locale.ENGLISH);

    public void sendBookingConfirmation(BookingConfirmedEvent booking) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");

        helper.setFrom(fromEmail,fromName);
        helper.setTo(booking.getContactEmail());
        helper.setSubject(buildSubject(booking));
        helper.setText(buildHtmlBody(booking));

        mailSender.send(mimeMessage);
    }

    private String buildHtmlBody(BookingConfirmedEvent booking) {
        Context ctx = new Context();
        ctx.setVariable("booking", booking);
        ctx.setVariable("passengerCount",
                booking.getPassengers()!=null?booking.getPassengers().size():1);
        ctx.setVariable("depDate",
                booking.getDepartureDateTime()!=null?
                        (booking.getDepartureDateTime().format(DATE_FMT)):"N/A");
        ctx.setVariable("depTime",
                booking.getDepartureDateTime()!=null ?
                booking.getDepartureDateTime().format(TIME_FMT) : "N/A");
        ctx.setVariable("arrDate",
                booking.getArrivalDateTime()!=null?
                booking.getArrivalDateTime().format(DATE_FMT):"N/A");
        ctx.setVariable("arrTime",
                booking.getArrivalDateTime()!=null?
                booking.getArrivalDateTime().format(TIME_FMT): "N/A");
        ctx.setVariable("paidAt",
                booking.getPaidAt()!=null?
                booking.getPaidAt().format(DATE_FMT):"N/A");
        ctx.setVariable("bookingDate",
                booking.getBookingDate()!=null?
                booking.getBookingDate().format(DATE_FMT):"N/A");

        double base = orZero(booking.getBaseFare());
        double taxes = orZero(booking.getTaxesAndFees());
        double seats = orZero(booking.getSeatFees());
        double ancillary = orZero(booking.getAncillaryFees());
        double meals = orZero(booking.getMealFees());
        double total = orZero(booking.getTotalAmount());

        ctx.setVariable("baseFareTotal", fmtString(base));
        ctx.setVariable("taxes", fmtString(taxes));
        ctx.setVariable("seatFees", fmtString(seats));
        ctx.setVariable("ancillaryFees", fmtString(ancillary));
        ctx.setVariable("mealsFees", fmtString(meals));
        ctx.setVariable("totalAmount", fmtString(total));

        ctx.setVariable("hasBaggage",
                booking.getCheckinBaggagePieces()!=null
                || booking.getCabinBaggagePieces()!=null);
        ctx.setVariable("checkinBaggage",baggageLabel(
                booking.getCheckinBaggagePieces(), booking.getCheckinBaggageWeightPerPiece()
        ));
        ctx.setVariable("cabinBaggage",baggageLabel(
                booking.getCabinBaggagePieces(),booking.getCabinBaggageWeightPerPiece()
        ));

        ctx.setVariable("cabinClassDisplay",cabinDisplayName(booking.getCabinClass()));

        return templateEngine.process("email/booking-confirmation", ctx);
    }

    private static double orZero(Double d) {
        return d != null ? d : 0.0;
    }

    private static String fmtString(double v){
        return String.format("%.2f", v);
    }

    private Object baggageLabel(Integer pieces,Double weightPerPiece){
        if(pieces==null && weightPerPiece==null) return "Not Included";
        if(pieces != null && weightPerPiece != null) return pieces+"\u00d7"+weightPerPiece.intValue()+" kg";
        if(pieces!=null)return pieces+" piece(s)";

        return weightPerPiece.intValue()+" kg";
    }

    private String buildSubject(BookingConfirmedEvent booking) {
        String depDate = booking.getDepartureDateTime()!=null?
                booking.getDepartureDateTime().format(DATE_FMT):"";
        return String.format("Booking Confirmation for | %s | %s \u2192%s | %s",
                booking.getBookingReference(),
                booking.getDepartureAirportCode(),
                booking.getArrivalAirportCode(),
                depDate);
    }

    private static String cabinDisplayName(String cabinClass){
        if(cabinClass==null) return "Economy";

        return switch (cabinClass){
            case "ECONOMY" -> "Economy";
            case "PREMIUM_ECONOMY" -> "Premium Economy";
            case "BUSINESS" -> "Business";
            case "FIRST"-> "First Class";
            default -> cabinClass;
        };
    }
}
