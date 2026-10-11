package com.zosh.service.impl;

import com.zosh.model.Airport;
import com.zosh.model.AirportMapper;
import com.zosh.model.City;
import com.zosh.payload.request.AirportRequest;
import com.zosh.payload.response.AirportResponse;
import com.zosh.repository.AirportRepository;
import com.zosh.repository.CityRepository;
import com.zosh.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    @Autowired
    private final AirportRepository repository;
    @Autowired
    private final CityRepository cityRepository;

    @Override
    public AirportResponse createAirport(AirportRequest request) throws Exception{
        if(repository.findByIataCode(request.getIataCode()).isPresent()){
            throw new Exception("Airport with iata = "+ request.getIataCode()+" already exists");
        }

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(()->new Exception("City not found"));

        Airport airport = AirportMapper.toEntity(request);
        airport.setCity(city);
        Airport savedAirport = repository.save(airport);
        return AirportMapper.toResponse(savedAirport);
    }

    @Override
    @Cacheable(cacheNames = "airports", key = "#id")
    public AirportResponse getAirportById(Long id) throws Exception{
        Airport airport = repository.findById(id)
                .orElseThrow(()->new Exception("Airport with id = "+ id+" does not exists"));
        return AirportMapper.toResponse(airport);
    }

    @Override
    @Cacheable(cacheNames = "allAirports")
    public List<AirportResponse> getAllAirports() {
        return repository.findAll().stream().map(AirportMapper::toResponse).toList();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "airports", key = "#id"),
            @CacheEvict(cacheNames = "allAirports",allEntries = true),
            @CacheEvict(cacheNames = "airportsByCity",allEntries = true)
    })
    public AirportResponse updateAirport(Long id, AirportRequest request) throws Exception{
        Airport airportInDB = repository.findById(id)
                .orElseThrow(()->new Exception("Airport with id = "+ id+" does not exists"));

        if(request.getIataCode()!=null
           && !airportInDB.getIataCode().equals(request.getIataCode())
                && repository.findByIataCode(request.getIataCode()).isPresent()){
                throw new Exception("Airport with given IATA code already exists");
        }

        AirportMapper.updateEntity(request,airportInDB);
        Airport updatedAirport = repository.save(airportInDB);
        return AirportMapper.toResponse(updatedAirport);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "airports", key = "#id"),
            @CacheEvict(cacheNames = "allAirports",allEntries = true),
            @CacheEvict(cacheNames = "airportsByCity",allEntries = true)
    })
    public void deleteAirport(Long id) throws Exception {
        Airport airport = repository.findById(id)
                .orElseThrow(()->new Exception("Airport with id = "+ id+" does not exists"));

        repository.delete(airport);
    }

    @Override
    @Cacheable(cacheNames = "airportsByCity", key = "#cityId")
    public List<AirportResponse> getAirportByCityId(Long cityId) {
        return repository.findByCityId(cityId)
                .stream()
                .map(AirportMapper::toResponse)
                .toList();
    }
}
