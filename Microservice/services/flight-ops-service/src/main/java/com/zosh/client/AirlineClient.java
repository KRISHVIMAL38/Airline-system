package com.zosh.client;

import com.zosh.payload.response.AircraftResponse;
import com.zosh.payload.response.AirlineResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "airline-core-service")
public interface AirlineClient {
    @GetMapping("/api/airlines/{Id}")
    AirlineResponse getAirlineById(
            @PathVariable("Id") Long Id);

    @GetMapping("/api/aircrafts/{Id}")
    AircraftResponse getAirCraft(@PathVariable Long Id);

    @GetMapping("/api/airlines/admin")
    AirlineResponse getAirlineByOwner(
            @RequestHeader("X-User-Id") Long userId);

}


