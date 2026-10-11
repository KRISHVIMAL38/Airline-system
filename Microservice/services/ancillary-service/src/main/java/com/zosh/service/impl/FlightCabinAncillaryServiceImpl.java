package com.zosh.service.impl;

import com.zosh.enums.AncillaryType;
import com.zosh.mapper.FlightCabinAncillaryMapper;
import com.zosh.mapper.InsuranceCoverageMapper;
import com.zosh.model.Ancillary;
import com.zosh.model.FlightCabinAncillary;
import com.zosh.model.InsuranceCoverage;
import com.zosh.payload.request.FlightCabinAncillaryRequest;
import com.zosh.payload.response.FlightCabinAncillaryResponse;
import com.zosh.payload.response.InsuranceCoverageResponse;
import com.zosh.repository.AncillaryRepository;
import com.zosh.repository.FlightCabinAncillaryRepository;
import com.zosh.repository.InsuranceCoverageRepository;
import com.zosh.service.FlightCabinAncillaryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightCabinAncillaryServiceImpl implements FlightCabinAncillaryService {
    @Autowired
    private FlightCabinAncillaryRepository repository;

    @Autowired
    private AncillaryRepository ancillaryRepository;

    @Autowired
    private InsuranceCoverageRepository insuranceCoverageRepository;

    @Override
    public FlightCabinAncillaryResponse createFlightCabinAncillary(FlightCabinAncillaryRequest request) throws EntityNotFoundException {
        Ancillary ancillary = ancillaryRepository.findById(request.getAncillaryId()).orElseThrow(()->
                new EntityNotFoundException("Ancillary not found with id: " + request.getAncillaryId()));

        FlightCabinAncillary flightCabinAncillary = FlightCabinAncillary.builder()
                .flightId(request.getFlightId())
                .cabinClassId(request.getCabinClassId())
                .ancillary(ancillary)
                .available(request.getAvailable())
                .maxQuantity(request.getMaxQuantity())
                .price(request.getPrice())
                .includedInFare(request.getIncludedInFare())
                .build();

        FlightCabinAncillary saved = repository.save(flightCabinAncillary);

        return convertToResponse(saved);
    }

    @Override
    public FlightCabinAncillaryResponse getById(Long id) throws EntityNotFoundException {
        FlightCabinAncillary flightCabinAncillary = repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("FlightCabinAncillary not found with id: " + id));

        return convertToResponse(flightCabinAncillary);
    }

    @Override
    public List<FlightCabinAncillaryResponse> getByFlightAndCabinClass(Long flightId, Long cabinClassId) {
        return repository.findByFlightIdAndCabinClassId(flightId,cabinClassId).stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<FlightCabinAncillaryResponse> getAllByIds(List<Long> ids) {
        return repository.findAllById(ids).stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public FlightCabinAncillaryResponse getByFlightIdAndCabinClassIdAndType(Long flightId, Long cabinClassId, AncillaryType type) {
        FlightCabinAncillary flightCabinAncillary = repository.findByFlightIdAndCabinClassIdAndAncillaryType(
                flightId,cabinClassId,type
        );
        return convertToResponse(flightCabinAncillary);
    }

    @Override
    public List<FlightCabinAncillaryResponse> getAllByFlightIdAndCabinClassIdAndType(Long flightId, Long cabinClassId, AncillaryType type) {
        return repository.findAllByFlightIdAndCabinClassIdAndAncillaryType(flightId,cabinClassId,type).stream()
                .map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public FlightCabinAncillaryResponse updateFlightCabinAncillary(Long id, FlightCabinAncillaryRequest request) {
        FlightCabinAncillary existing = repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("FlightCabinAncillary not found with id: " + id));

        existing.setAvailable(request.getAvailable()!=null?request.getAvailable():existing.getAvailable());
        existing.setMaxQuantity(request.getMaxQuantity()!=null?request.getMaxQuantity():existing.getMaxQuantity());
        existing.setPrice(request.getPrice()!=null?request.getPrice():existing.getPrice());
        existing.setIncludedInFare(request.getIncludedInFare()!=null?request.getIncludedInFare():existing.getIncludedInFare());

        FlightCabinAncillary saved = repository.save(existing);
        return convertToResponse(saved);
    }

    @Override
    public void deleteFlightCabinAncillary(Long id) {
        FlightCabinAncillary existing = repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("FlightCabinAncillary not found with id: " + id));

        repository.delete(existing);
    }

    @Override
    public Double calculateAncillaryPrice(List<Long> ancillaryIds) {
        List<FlightCabinAncillary> ancillaries = repository.findAllById(ancillaryIds);
        Double price = 0.0;
        for (FlightCabinAncillary ancillary : ancillaries) {
            price+=ancillary.getPrice();
        }
        return price;
    }

    private FlightCabinAncillaryResponse convertToResponse(FlightCabinAncillary saved) {
        List<InsuranceCoverage> coverages = insuranceCoverageRepository.findByAncillaryId(saved.getAncillary().getId());
        List<InsuranceCoverageResponse> coverages2 = coverages.stream().map(InsuranceCoverageMapper::toResponse).collect(Collectors.toList());
        return FlightCabinAncillaryMapper.toResponse(saved,coverages2);
    }
}
