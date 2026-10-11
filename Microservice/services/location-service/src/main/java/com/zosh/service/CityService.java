package com.zosh.service;

import com.zosh.payload.request.CityRequest;
import com.zosh.payload.response.CityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {
    CityResponse createCity(CityRequest request) throws Exception;

    CityResponse getCityBiId(Long Id) throws Exception;
    Page<CityResponse> getAllCities(Pageable pageable);
    Page<CityResponse> searchCities(String keyword, Pageable pageable);
    Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable);

    CityResponse updateCity(Long Id , CityRequest request) throws Exception;
    void deleteCity(Long Id) throws Exception;

    boolean cityExists(String cityCode);

}
