package com.zosh.service;

import com.zosh.model.Passenger;
import com.zosh.payload.request.PassengerRequest;

public interface PassengerService {
    Passenger createPassenger(PassengerRequest request,Long userId);
}
