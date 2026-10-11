package com.zosh.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "seat-service")
public interface SeatClient {
    @PostMapping("/api/seat-instances/price/total")
    public Double getSeatPrice(@RequestBody List<Long> seatInstanceIds);
}
