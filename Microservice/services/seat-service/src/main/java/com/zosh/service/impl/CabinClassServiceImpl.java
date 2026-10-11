package com.zosh.service.impl;

import com.zosh.enums.CabinClassType;
import com.zosh.mapper.CabinClassMapper;
import com.zosh.model.CabinClass;
import com.zosh.payload.request.CabinClassRequest;
import com.zosh.payload.response.CabinClassResponse;
import com.zosh.repository.CabinClassRepository;
import com.zosh.service.CabinClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CabinClassServiceImpl implements CabinClassService {

    @Autowired
    private CabinClassRepository cabinClassRepository;

    @Override
    public CabinClassResponse createCabinClass(CabinClassRequest request) throws Exception {
        if(cabinClassRepository.existsByCodeAndAircraftId(request.getCode(), request.getAircraftId())){
            throw new Exception("Cabin class with given code and aircraft id already exists");
        }
        CabinClass cabinClass = CabinClassMapper.toCabinClass(request);
        CabinClass saved = cabinClassRepository.save(cabinClass);
        return CabinClassMapper.toResponse(saved,null);
    }

    @Override
    public CabinClassResponse getCabinClassById(Long id) throws Exception {
        CabinClass cabinClass = cabinClassRepository.findById(id).orElseThrow(()->
                new Exception("cabin class not found with id = "+id));

        return CabinClassMapper.toResponse(cabinClass,cabinClass.getSeatMap());
    }

    @Override
    public List<CabinClassResponse> getCabinClassesByAircraftId(Long aircraftId) {
        List<CabinClass> cabinClasses = cabinClassRepository.findByAircraftId(aircraftId);

        return cabinClasses.stream().map(cc->CabinClassMapper.toResponse(cc,cc.getSeatMap())).collect(Collectors.toList());
    }

    @Override
    public CabinClassResponse getCabinClassByAircraftAndName(Long aircraftId, CabinClassType name) {
        CabinClass cabinClass = cabinClassRepository.findByAircraftIdAndName(aircraftId,name);

        return CabinClassMapper.toResponse(cabinClass,null);
    }

    @Override
    public CabinClassResponse updateCabinClass(Long id, CabinClassRequest request) throws Exception {
        CabinClass existing = cabinClassRepository.findById(id).orElseThrow(()->
                new Exception("cabin class not found with id = "+id));

         if(cabinClassRepository.existsByCodeAndAircraftIdAndIdNot(request.getCode(),
                 request.getAircraftId(),
                 id)){
             throw new Exception("Cabin class with id already exists");
         }

        CabinClassMapper.updateEntity(request,existing);
        CabinClass updated = cabinClassRepository.save(existing);
        return CabinClassMapper.toResponse(updated,updated.getSeatMap());
    }

    @Override
    public void deleteCabinClassById(Long id) throws Exception {
        CabinClass existing = cabinClassRepository.findById(id).orElseThrow(()->
                new Exception("cabin class not found with id = "+id));

        cabinClassRepository.delete(existing);
    }
}
