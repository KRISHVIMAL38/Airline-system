package com.zosh.service.impl;

import com.zosh.enums.SeatAvailabilityStatus;
import com.zosh.mapper.SeatInstanceMapper;
import com.zosh.model.SeatInstance;
import com.zosh.payload.response.SeatInstanceResponse;
import com.zosh.repository.SeatInstanceRepository;
import com.zosh.service.SeatInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatInstanceServiceImpl implements SeatInstanceService {

    @Autowired
    private SeatInstanceRepository seatInstanceRepository;

    @Override
    public Double calculateTotalSeatPrice(List<Long> seatInstanceIds) {
        List<SeatInstance> seatInstances = seatInstanceRepository.findAllById(seatInstanceIds);

        double totalPrice = 0;

        for (SeatInstance seatInstance : seatInstances) {
            double seatPremium = seatInstance.getPremiumSuperCharge()!=null?seatInstance.getPremiumSuperCharge():0;
            totalPrice+=seatPremium;
        }
        return totalPrice;
    }

    @Override
    public SeatInstanceResponse updateSeatInstanceStatus(Long seatInstanceId, SeatAvailabilityStatus status) {
        SeatInstance seatInstance = seatInstanceRepository.findById(seatInstanceId).orElse(null);

        if (seatInstance == null) {return null;}
        seatInstance.setStatus(status);
        seatInstanceRepository.save(seatInstance);
        return SeatInstanceMapper.toResponse(seatInstance);
    }
}
