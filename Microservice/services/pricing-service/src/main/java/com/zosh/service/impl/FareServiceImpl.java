package com.zosh.service.impl;

import com.zosh.mapper.FareMapper;
import com.zosh.model.Fare;
import com.zosh.payload.request.FareRequest;
import com.zosh.payload.response.FareResponse;
import com.zosh.repository.FareRepository;
import com.zosh.service.FareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FareServiceImpl implements FareService {
    @Autowired
    private FareRepository fareRepository;

    @Override
    public FareResponse createFare(FareRequest request) throws Exception {
        if(fareRepository.existsByFlightIdAndCabinClassIdAndName(
                request.getFlightId(),
                request.getCabinClassId(),
                request.getName()
        )){
            throw new Exception("Fare Already exists with provided name");
        }

        Fare fare = FareMapper.toFair(request);
        Fare saved = fareRepository.save(fare);

        return FareMapper.toResponse(saved);
    }

    @Override
    public FareResponse getFairById(Long Id) throws Exception {
        Fare fare = fareRepository.findById(Id)
                .orElseThrow(()->new Exception("fare not found with id = "+Id));


        return FareMapper.toResponse(fare);
    }

    @Override
    public List<FareResponse> getFairByFlightIdAndCabinClassId(Long flightId, Long cabinClassId) throws Exception {
        List<Fare> fares = fareRepository.findByFlightIdAndCabinClassId(flightId,cabinClassId);
        if(fares == null){
            throw new Exception("Fare not found with flightId = "+flightId+" And cabinClassId = "+cabinClassId);
        }

        return fares.stream().map(FareMapper::toResponse).toList();

    }

    @Override
    public FareResponse updateFair(Long Id, FareRequest request) throws Exception {
        Fare fare = fareRepository.findById(Id).orElseThrow(()->
                new Exception("fare Not found with id = "+Id));

        if(fareRepository.existsByFlightIdAndCabinClassIdAndNameAndIdNot(
                request.getFlightId(),
                request.getCabinClassId(),
                request.getName(),
                fare.getId())){
            throw new Exception("fare Not found with id = "+Id);
        }

        FareMapper.updateEntity(request,fare);
        Fare updated = fareRepository.save(fare);
        return FareMapper.toResponse(updated);
    }

    @Override
    public void deleteFare(Long Id) throws Exception {
        Fare fare = fareRepository.findById(Id).orElseThrow(()->
                new Exception("fare Not found with id = "+Id));

        fareRepository.delete(fare);
    }

    @Override
    public List<Fare> getAllFares() {
        return fareRepository.findAll();
    }

    /*
    * Flight 101
    * Fare 1 ->5000
    * Fare 2 ->4500
    * Fare 3 ->4000
    *
    *   flightIds = [101,102,103]
    * cabinClassId = 2 Economy
    *
    * */

    @Override
    public Map<Long, FareResponse> getLowestFarePerFlight(List<Long> flightIds, Long cabinClassId) {
        if(flightIds==null || flightIds.isEmpty()) return Map.of();

        List<Fare> fares = fareRepository.findByFlightIdInAndCabinClassId(flightIds,cabinClassId);

        Map<Long,FareResponse> result = fares.stream().collect(Collectors.toMap(
                Fare::getFlightId,
                fare->fare,
                (existing,candidate)->
                        candidate.getTotalPrice()<existing.getTotalPrice()
                ?candidate:existing
        )).entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e->FareMapper.toResponse(e.getValue())
        ));

        return result;
    }

    @Override
    public Map<Long, FareResponse> getFaresByIds(List<Long> ids) {
        List<Fare> fares = fareRepository.findAllById(ids);
        return fares.stream().collect(Collectors.toMap(Fare::getId,FareMapper::toResponse));
    }

    @Override
    public FareResponse getLowestFareForFlightAndCabinClass(Long flightId, Long cabinClassId) {
        List<Fare> fares = fareRepository.findByFlightIdAndCabinClassId(flightId,cabinClassId);

        Fare lowestFare = fares.stream().min(Comparator.comparingDouble(Fare::getTotalPrice)).orElse(null);

        return FareMapper.toResponse(lowestFare);
    }
}
