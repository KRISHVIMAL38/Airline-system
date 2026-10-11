package com.zosh.service.gateway;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.zosh.dto.UserDTO;
import com.zosh.model.Payment;
import com.zosh.payload.response.PaymentLinkResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RazorPayService {

    @Value("${razorpay.api.key}")
    private String razorpayKeyId;

    @Value("${razorpay.api.secret}")
    private String razorpaySecret;

    @Value("${razorpay.callback.base-url}")
    private String callbackUrl;

    public PaymentLinkResponse createPaymentLink(UserDTO user, Payment payment) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId,razorpaySecret);

        BigDecimal amount = BigDecimal.valueOf(payment.getAmount());

        Long amountInPaise = amount.multiply(new BigDecimal(100)).longValue();

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",amountInPaise);
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("description",payment.getTransactionId());

        //customer details
        JSONObject customer =  new JSONObject();
        customer.put("name",user.getFullName());
        customer.put("email",user.getEmail());
        if(user.getPhone()!=null){
            customer.put("contact",user.getPhone());
        }

        paymentLinkRequest.put("customer",customer);

        //notification settings
        JSONObject notify =  new JSONObject();
        notify.put("email",true);
        notify.put("sms",user.getPhone()!=null);
        paymentLinkRequest.put("notify",notify);

        //enable reminders
        paymentLinkRequest.put("reminder_enable",true);

        //callback configuration
        String successUrl = callbackUrl + "/booking-success/"+payment.getBookingId();

        paymentLinkRequest.put("callback_url",successUrl);
        paymentLinkRequest.put("callback_method","get");

        //additional metadata for tracking
        JSONObject notes = new JSONObject();
        notes.put("user_id",user.getId());
        notes.put("payment_id",payment.getId());
        notes.put("booking_id",payment.getBookingId());

        paymentLinkRequest.put("notes",notes);

        //create payment link
        PaymentLink paymentLink = razorpay.paymentLink.create(paymentLinkRequest);
        String paymentUrl = paymentLink.get("short_url");
        String paymentLinkId = paymentLink.get("id");

        PaymentLinkResponse paymentLinkResponse =  PaymentLinkResponse.builder()
                .payment_link_id(paymentLinkId)
                .payment_link_url(paymentUrl)
                .build();

        return paymentLinkResponse;
    }

    public JSONObject fetchPaymentDetails(String paymentId) throws RazorpayException {
        RazorpayClient razorpay =  new RazorpayClient(razorpayKeyId,razorpaySecret);

        //This is not our model class
        com.razorpay.Payment payment = razorpay.payments.fetch(paymentId);

        return payment.toJson();

    }
}
