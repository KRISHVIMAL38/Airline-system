package com.zosh.service.impl;

import com.zosh.mapper.CityMapper;
import com.zosh.model.City;
import com.zosh.payload.request.CityRequest;
import com.zosh.payload.response.CityResponse;
import com.zosh.repository.CityRepository;
import com.zosh.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public CityResponse createCity(CityRequest request) throws Exception {
        if(cityRepository.existsByCityCode(request.getCityCode())){
            throw new Exception("City with given code already exists");
        }

        City city = CityMapper.toEntity(request);
        City savedCity = cityRepository.save(city);

        return CityMapper.toResponse(savedCity);
     }

    @Override
    @Cacheable(cacheNames = "cities",key = "#id")
    public CityResponse getCityBiId(Long Id) throws Exception{
        City city = cityRepository.findById(Id).orElseThrow(
                ()-> new Exception("City not found with id = "+Id)
        );

        return CityMapper.toResponse(city);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "cities",key = "#id"),
            @CacheEvict(cacheNames = "citiesByCode",allEntries = true),
    })
    public CityResponse updateCity(Long Id, CityRequest request) throws Exception{
        City city = cityRepository.findById(Id).orElseThrow(
                ()-> new Exception("City not found with id = "+Id)
        );

        if(cityRepository.existsByCityCodeAndIdNot(request.getCityCode(),Id)){
            throw new Exception("City with given code already exists");
        }

        City updatedCity = cityRepository.save(CityMapper.updateEntiity(city,request));
        return CityMapper.toResponse(updatedCity);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "cities",key = "#id"),
            @CacheEvict(cacheNames = "citiesByCode",allEntries = true),
    })
    public void deleteCity(Long Id) throws Exception {
        City city = cityRepository.findById(Id).orElseThrow(
                ()-> new Exception("City not found with id = "+Id)
        );

        cityRepository.delete(city);
    }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable)
                .map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
        return cityRepository.searchByKeyword(keyword,pageable)
                .map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable) {
        return cityRepository.findByCityCodeIgnoreCase(countryCode,pageable)
                .map(CityMapper::toResponse);
    }

    @Override
    public boolean cityExists(String cityCode) {
        return cityRepository.existsByCityCode(cityCode);
    }

}
