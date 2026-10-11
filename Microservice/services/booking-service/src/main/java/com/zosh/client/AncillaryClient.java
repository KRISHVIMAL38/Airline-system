package com.zosh.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ancillary-service")
public interface AncillaryClient {

    @PostMapping("/api/flight-cabin-ancillaries/price/total")
    Double calculateAncillariesPrice(
            @RequestBody List<Long> flightCabinAncillaryIds);

    @PostMapping("api/flight-meals/price/total")
    Double calculateMealPrice(
            @RequestBody List<Long> requests);
}
