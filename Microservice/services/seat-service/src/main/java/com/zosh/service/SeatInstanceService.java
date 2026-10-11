package com.zosh.service;

import com.zosh.enums.SeatAvailabilityStatus;
import com.zosh.payload.response.SeatInstanceResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface SeatInstanceService {

    Double calculateTotalSeatPrice(List<Long> seatInstanceIds);

    SeatInstanceResponse updateSeatInstanceStatus(Long seatInstanceId,
                                                  SeatAvailabilityStatus status);
}
