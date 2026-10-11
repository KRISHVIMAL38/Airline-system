package com.zosh.service.impl;

import com.zosh.enums.AirlineStatus;
import com.zosh.mapper.AirlineMapper;
import com.zosh.model.Airline;
import com.zosh.payload.request.AirlineRequest;
import com.zosh.payload.response.AirlineDropdownItem;
import com.zosh.payload.response.AirlineResponse;
import com.zosh.repository.AirlineRepository;
import com.zosh.service.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AirlineServiceImpl implements AirlineService {
    @Autowired
    AirlineRepository repository;
    @Override
    public AirlineResponse createAirline(AirlineRequest request, Long ownerId) {
        Airline airline = AirlineMapper.toAirline(request,ownerId);
        Airline savedAirline = repository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public AirlineResponse getAirlineByOwner(Long ownerId) throws Exception {
        Airline airline = repository.findByOwnerId(ownerId)
                .orElseThrow(()->new Exception("Airline not found"));

        return AirlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse getAirlineById(Long Id) throws Exception {
        Airline airline = repository.findById(Id)
                .orElseThrow(()->new Exception("Airline not found"));

        return AirlineMapper.toResponse(airline);
    }

    @Override
    public Page<AirlineResponse> getAllAirlines(Pageable pageable) {
        return repository.findAll(pageable)
                .map(AirlineMapper::toResponse);
    }

    @Override
    public AirlineResponse updateAirline(AirlineRequest req, Long ownerId) throws Exception {
        Airline airline = repository.findByOwnerId(ownerId)
                .orElseThrow(()->new Exception("Airline not found"));

        AirlineMapper.updateEntity(airline,req);
        airline = repository.save(airline);
        return AirlineMapper.toResponse(airline);
    }

    @Override
    public void deleteAirline(Long airlineId,Long ownerId) throws Exception {
        Airline airline = repository.findById(ownerId)
                .orElseThrow(() -> new Exception("Airline not found"));

        repository.delete(airline);
    }

    @Override
    public AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status) throws Exception {
        Airline airline = repository.findById(airlineId)
                .orElseThrow(() -> new Exception("Airline not found"));

        airline.setStatus(status);
        return AirlineMapper.toResponse(repository.save(airline));
    }

    @Override
    public List<AirlineDropdownItem> getAirlineDropdown() {
        return repository.findByStatus(AirlineStatus.ACTIVE)
                .stream()
                .map(a->AirlineDropdownItem.builder()
                        .id(a.getId())
                        .iataCode(a.getIataCode())
                        .iacoCode(a.getIcaoCode())
                        .name(a.getName())
                        .logoUrl(a.getLogoUrl())
                        .build()).toList();

    }
}
