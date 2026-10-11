package com.zosh.mapper;

import com.zosh.dto.PaymentDTO;
import com.zosh.model.Payment;

import java.time.ZoneId;

public class PaymentMapper {

    public static PaymentDTO toPaymentDTO(Payment payment) {
        if (payment == null) return null;

        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setGateway(payment.getProvider());
        dto.setAmount(payment.getAmount()!=null?payment.getAmount().longValue():0);
        dto.setTransactionId(payment.getTransactionId());
        dto.setStatus(payment.getStatus());
        dto.setUserId(payment.getUserId());
        dto.setBookingId(payment.getBookingId());

        if(payment.getCreatedAt()!=null){
            dto.setCreatedAt(payment.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDateTime());
            dto.setInitiatedAt(payment.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }

        if (payment.getUpdatedAt()!=null){
            dto.setUpdatedAt(payment.getUpdatedAt().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }

        return dto;
    }
}
