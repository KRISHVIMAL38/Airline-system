package com.zosh.controller;

import com.zosh.payload.request.CityRequest;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.CityResponse;
import com.zosh.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cities")
public class CityController {
    @Autowired
    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CityResponse> createCity( @Valid @RequestBody CityRequest request) throws Exception{
            CityResponse response = cityService.createCity(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<CityResponse> getCityById( @PathVariable Long Id) throws Exception{
        CityResponse response = cityService.getCityBiId(Id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping()
    public ResponseEntity<Page<CityResponse>> getAllCities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) throws Exception{

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection),sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);
        return ResponseEntity.ok(cityService.getAllCities(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CityResponse>> searchCities(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page) throws Exception{

        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(cityService.searchCities(keyword,pageable));
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<Page<CityResponse>> searchCitiesByCountryCode(
            @RequestParam String countryCode,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "0") int page) throws Exception{

        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(cityService.getCitiesByCountryCode(countryCode.toUpperCase(),pageable));
    }

    @GetMapping("/exists/{cityCode}")
    public ResponseEntity<Boolean> checkCityExists(@PathVariable String cityCode){
        return ResponseEntity.ok(cityService.cityExists(cityCode));
    }

    @PutMapping("/{Id}")
    public ResponseEntity<CityResponse> updateCity(
            @PathVariable Long Id,
            @Valid @RequestBody CityRequest request) throws Exception{
            return ResponseEntity.ok(cityService.updateCity(Id,request));
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<ApiResponse> deleteCity(@PathVariable Long Id)
        throws Exception{
        cityService.deleteCity(Id);
        return ResponseEntity.ok(new ApiResponse("City Deleted successfully"));
    }



}
