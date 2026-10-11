package com.zosh.service.impl;

import com.zosh.client.AirlineClient;
import com.zosh.mapper.AncillaryMapper;
import com.zosh.mapper.InsuranceCoverageMapper;
import com.zosh.model.Ancillary;
import com.zosh.model.InsuranceCoverage;
import com.zosh.payload.request.AncillaryRequest;
import com.zosh.payload.response.AirlineResponse;
import com.zosh.payload.response.AncillaryResponse;
import com.zosh.payload.response.InsuranceCoverageResponse;
import com.zosh.repository.AncillaryRepository;
import com.zosh.repository.InsuranceCoverageRepository;
import com.zosh.service.AncillaryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AncillaryServiceImpl implements AncillaryService {

    @Autowired
    private AncillaryRepository ancillaryRepository;

    @Autowired
    private InsuranceCoverageRepository insuranceCoverageRepository;

    @Autowired
    private AirlineClient airlineClient;

    @Override
    public AncillaryResponse createAncillary(Long userId, AncillaryRequest request) {
        AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);

        Ancillary ancillary = AncillaryMapper.toEntity(request,airlineResponse.getOwnerId());

        Ancillary savedAncillary = ancillaryRepository.save(ancillary);
        return AncillaryMapper.toResponse(savedAncillary,null);
    }

    @Override
    public AncillaryResponse getAncillaryById(Long id) throws EntityNotFoundException {
        Ancillary ancillary = ancillaryRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Ancillary not found with id " + id));

        List<InsuranceCoverageResponse> coverages = insuranceCoverageRepository.findByAncillaryId(ancillary.getId())
                .stream().map(InsuranceCoverageMapper::toResponse).collect(Collectors.toList());

        return AncillaryMapper.toResponse(ancillary,coverages);
    }

    @Override
    public List<AncillaryResponse> getAncillaryByAirlineId(Long userId) {
        AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);

        return ancillaryRepository.findByAirlineId(airlineResponse.getId()).stream().map(
                //todo add Insurance coverage
                ancillary -> AncillaryMapper.toResponse(ancillary,insuranceCoverageRepository.findByAncillaryId(ancillary.getId())
                        .stream().map(InsuranceCoverageMapper::toResponse).collect(Collectors.toList()))
                )
                .collect(Collectors.toList());
    }

    @Override
    public AncillaryResponse updateAncillary(Long id, AncillaryRequest request)
    throws EntityNotFoundException {
        Ancillary ancillary = ancillaryRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Ancillary not found with id " + id));

        ancillary.setName(request.getName());
        ancillary.setDescription(request.getDescription());
        ancillary.setType(request.getAncillaryType());
        ancillary.setRfic(request.getRfic());
        ancillary.setDisplayOrder(request.getDisplayOrder());
        ancillary.setSubType(request.getSubtype());
        ancillary.setAncillaryMetaData(request.getMetadata());

        Ancillary updatedAncillary = ancillaryRepository.save(ancillary);

        List<InsuranceCoverageResponse> coverages = insuranceCoverageRepository.findByAncillaryId(updatedAncillary.getId())
                .stream().map(InsuranceCoverageMapper::toResponse).collect(Collectors.toList());

        return AncillaryMapper.toResponse(updatedAncillary,coverages);
    }

    @Override
    public void deleteAncillary(Long id) throws EntityNotFoundException {
        Ancillary ancillary = ancillaryRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Ancillary not found with id " + id));

        ancillaryRepository.delete(ancillary);
    }
}
