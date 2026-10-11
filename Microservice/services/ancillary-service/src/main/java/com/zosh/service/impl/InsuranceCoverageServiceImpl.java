package com.zosh.service.impl;

import com.zosh.mapper.AncillaryMapper;
import com.zosh.mapper.InsuranceCoverageMapper;
import com.zosh.model.Ancillary;
import com.zosh.model.InsuranceCoverage;
import com.zosh.payload.request.InsuranceCoverageRequest;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.InsuranceCoverageResponse;
import com.zosh.repository.AncillaryRepository;
import com.zosh.repository.InsuranceCoverageRepository;
import com.zosh.service.InsuranceCoverageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsuranceCoverageServiceImpl implements InsuranceCoverageService {
    @Autowired
    private InsuranceCoverageRepository insuranceCoverageRepository;

    @Autowired
    private AncillaryRepository ancillaryRepository;

    @Override
    public InsuranceCoverageResponse createInsuranceCoverage(InsuranceCoverageRequest request) throws EntityNotFoundException{
        Ancillary ancillary = ancillaryRepository.findById(request.getAncillaryId())
                .orElseThrow(()->new EntityNotFoundException(
                        "Ancillary Id not found with id = "+request.getAncillaryId()));

        InsuranceCoverage insuranceCoverage = InsuranceCoverageMapper.toEntity(request,ancillary);
        InsuranceCoverage saved = insuranceCoverageRepository.save(insuranceCoverage);
        return InsuranceCoverageMapper.toResponse(saved);
    }

    @Override
    public InsuranceCoverageResponse getInsuranceCoverageById(Long ancillaryId) {
        InsuranceCoverage insuranceCoverage = insuranceCoverageRepository.findById(ancillaryId)
                .orElseThrow(()->new EntityNotFoundException("ancillary not found with id = "+ancillaryId));
        return InsuranceCoverageMapper.toResponse(insuranceCoverage);
    }

    @Override
    public InsuranceCoverageResponse updateInsuranceCoverageById(Long id, InsuranceCoverageRequest request)
    throws EntityNotFoundException {
        InsuranceCoverage existing = insuranceCoverageRepository.findById(id)
                        .orElseThrow(()->new EntityNotFoundException("Coverage not found with id = "+id));

        Ancillary ancillary = new Ancillary();
        if(request.getAncillaryId()!=null){
             ancillary = ancillaryRepository.findById(request.getAncillaryId())
                    .orElseThrow(()->new EntityNotFoundException(
                            "Ancillary Id not found with id = "+request.getAncillaryId()));
        }

        InsuranceCoverageMapper.updateEntity(existing,request,ancillary);
        insuranceCoverageRepository.save(existing);

        return InsuranceCoverageMapper.toResponse(existing) ;
    }

    @Override
    public ApiResponse deleteInsuranceCoverageById(Long id) {
        InsuranceCoverage existing = insuranceCoverageRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Coverage not found with id = "+id));
        insuranceCoverageRepository.delete(existing);
        return new ApiResponse("coverage deleted successfully");
    }

    @Override
    public List<InsuranceCoverageResponse> getCoverageByAncillaryId(Long ancillaryId) {
        List<InsuranceCoverage> coverages = insuranceCoverageRepository.findByAncillaryId(ancillaryId);

        return coverages.stream().map(InsuranceCoverageMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<InsuranceCoverageResponse> getActiveCoverageByAncillaryId(Long ancillaryId) {
        List<InsuranceCoverage> coverages = insuranceCoverageRepository.findByAncillaryIdAndActiveTrue(ancillaryId);

        return coverages.stream().map(InsuranceCoverageMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<InsuranceCoverageResponse> getAllCoverages() {
        return insuranceCoverageRepository.findAll().stream()
                .map(InsuranceCoverageMapper::toResponse).collect(Collectors.toList());
    }
}
