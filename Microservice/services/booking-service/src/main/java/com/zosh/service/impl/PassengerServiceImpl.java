package com.zosh.service.impl;

import com.zosh.mapper.PassengerMapper;
import com.zosh.model.Passenger;
import com.zosh.payload.request.PassengerRequest;
import com.zosh.repository.PassengerRepository;
import com.zosh.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService {
    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public Passenger createPassenger(PassengerRequest request, Long userId) {
        Passenger passenger = PassengerMapper.toEntity(request);

        passenger.setPrimaryUserId(userId);

        Passenger saved;
        saved = passengerRepository.save(passenger);

        return saved;
    }

}
