package com.zosh.mapper;

import com.zosh.model.Ticket;
import com.zosh.payload.response.TicketResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class TicketMapper {
    public static TicketResponse toResponse(Ticket ticket) {
        if (ticket == null) return null;

        return TicketResponse.builder()
                .id(ticket.getId())
                .build();
    }
}
