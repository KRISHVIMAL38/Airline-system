package com.zosh.service.impl;

import com.zosh.mapper.BaggagePolicyMapper;
import com.zosh.model.BaggagePolicy;
import com.zosh.model.Fare;
import com.zosh.payload.request.BaggagePolicyRequest;
import com.zosh.payload.response.BaggagePolicyResponse;
import com.zosh.repository.BaggagePolicyRepository;
import com.zosh.repository.FareRepository;
import com.zosh.service.BaggagePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaggagePolicyServiceImpl implements BaggagePolicyService {
    @Autowired
    private BaggagePolicyRepository baggagePolicyRepository;

    @Autowired
    private FareRepository fareRepository;

    @Override
    public BaggagePolicyResponse createBaggagePolicy(BaggagePolicyRequest request) throws Exception {
        Fare fare = fareRepository.findById(request.getFareId()).orElseThrow(
                ()->new Exception("Fare not found"));

        if(baggagePolicyRepository.existsByFareId(fare.getId())){
            throw new Exception("Baggage Policy already exists");
        }

        BaggagePolicy baggagePolicy = BaggagePolicyMapper.toEntity(request,fare);

        BaggagePolicy saved = baggagePolicyRepository.save(baggagePolicy);

        return BaggagePolicyMapper.toResponse(saved);
    }

    @Override
    public BaggagePolicyResponse getBaggagePolicyById(Long id) throws Exception {
        BaggagePolicy baggagePolicy = baggagePolicyRepository.findById(id).orElseThrow(
                ()-> new Exception("Policy Not found"));
        return BaggagePolicyMapper.toResponse(baggagePolicy);
    }

    @Override
    public BaggagePolicyResponse getBaggagePolicyByFareId(Long fareId) {
        BaggagePolicy baggagePolicy = baggagePolicyRepository.findByFareId(fareId);
        return BaggagePolicyMapper.toResponse(baggagePolicy);
    }

    @Override
    public List<BaggagePolicyResponse> getBaggagePoliciesByAirlineId(Long airlineId) {
        return baggagePolicyRepository.findByAirlineId(airlineId)
                .stream().map(BaggagePolicyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BaggagePolicyResponse updateBaggagePolicy(Long id, BaggagePolicyRequest request) throws Exception {
        BaggagePolicy baggagePolicy = baggagePolicyRepository.findById(id).orElseThrow(
                ()-> new Exception("Policy Not found"));
        BaggagePolicyMapper.updateEntity(request,baggagePolicy);

        BaggagePolicy updated = baggagePolicyRepository.save(baggagePolicy);
        return BaggagePolicyMapper.toResponse(updated);
    }

    @Override
    public void deleteBaggagePolicy(Long id) throws Exception {
        BaggagePolicy baggagePolicy = baggagePolicyRepository.findById(id).orElseThrow(
                ()-> new Exception("Policy Not found"));

        baggagePolicyRepository.delete(baggagePolicy);
    }
}
