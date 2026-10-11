package com.zosh.service.integration;

import com.zosh.client.PricingClient;
import com.zosh.payload.response.FareResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FareIntegrationService {
    private final PricingClient pricingClient;
    public Double calculateFareTotal(Long fareId) {
        FareResponse fareResponse = pricingClient.getFairById(fareId);
        Double baseFare = fareResponse.getBaseFare()!=null?fareResponse.getBaseFare():0.0;
        Double taxesAndFees = fareResponse.getTaxesAndFees()!=null?fareResponse.getTaxesAndFees():0;
        Double airlineFees = fareResponse.getAirlineFees()!=null?fareResponse.getAirlineFees():0.0;

        return baseFare + taxesAndFees + airlineFees;

    }
}

