package com.zosh.service.impl;

import com.zosh.mapper.FareRulesMapper;
import com.zosh.model.Fare;
import com.zosh.model.FareRules;
import com.zosh.payload.request.FareRulesRequest;
import com.zosh.payload.response.FareRulesResponse;
import com.zosh.repository.FareRepository;
import com.zosh.repository.FareRulesRepository;
import com.zosh.service.FareRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FareRulesServiceImpl implements FareRulesService {
    @Autowired
    FareRepository fareRepository;

    @Autowired
    FareRulesRepository fareRulesRepository;

    @Override
    public FareRulesResponse createFareRules(FareRulesRequest request) throws Exception {
        Fare fare = fareRepository.findById(request.getFareId())
                .orElseThrow(()->new Exception("Fare not found"));

        if (fareRulesRepository.existsByFareId(request.getFareId())){
            throw new Exception("Fare already exists");
        }

        FareRules fareRules = FareRulesMapper.toFareRules(request,fare);
        FareRules saved = fareRulesRepository.save(fareRules);

        return FareRulesMapper.toResponse(saved);
    }

    @Override
    public FareRulesResponse getFareRulesById(Long id) throws Exception {
        FareRules fareRules = fareRulesRepository.findById(id).orElseThrow(()->
                new Exception("fare rule not found"));
        return FareRulesMapper.toResponse(fareRules);
    }

    @Override
    public FareRulesResponse getFareRulesByFareId(Long fareId) {
        FareRules fareRules = fareRulesRepository.findByFareId(fareId);

        return FareRulesMapper.toResponse(fareRules);
    }

    @Override
    public List<FareRulesResponse> getFareRulesByAirlineId(Long airlineId) {
        return fareRulesRepository.getFareRulesByAirlineId(airlineId)
                .stream().map(FareRulesMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FareRulesResponse updateFareRules(Long id, FareRulesRequest request) throws Exception {
        FareRules existing = fareRulesRepository.findById(id).orElseThrow(()->
                new Exception("fare rule not found"));
        FareRulesMapper.updateEntity(request,existing);

        FareRules updatedFareRules = fareRulesRepository.save(existing);
        return FareRulesMapper.toResponse(updatedFareRules);
    }

    @Override
    public void deleteFareRules(Long id) throws Exception {
        FareRules existing = fareRulesRepository.findById(id).orElseThrow(()->
                new Exception("fare rule not found"));

        fareRulesRepository.delete(existing);
    }
}
