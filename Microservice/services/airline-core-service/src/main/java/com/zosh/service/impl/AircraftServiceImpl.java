package com.zosh.service.impl;

import com.zosh.mapper.AircraftMapper;
import com.zosh.model.Aircraft;
import com.zosh.model.Airline;
import com.zosh.payload.request.AircraftRequest;
import com.zosh.payload.response.AircraftResponse;
import com.zosh.repository.AircraftRepository;
import com.zosh.repository.AirlineRepository;
import com.zosh.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AircraftServiceImpl implements AircraftService {
    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Override
    public AircraftResponse createAircraft(AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(()->new
                        Exception("Airline with this owner does not exists"));

        Aircraft aircraft = AircraftMapper.toAircraft(request,airline);

        if(aircraftRepository.existsByCodeAndIdNot(request.getCode(),aircraft.getId())){
            throw new Exception("Aircraft already exists with id "+request.getCode());
        }

        if(aircraft.getSeatingCapacity()<=aircraft.getTotalSeats()){
            throw new Exception("Total seats must be less than or equal to seating capacity");
        }
        Aircraft savedAircraft = aircraftRepository.save(aircraft);

        return AircraftMapper.toResponse(savedAircraft);
    }

    @Override
    public AircraftResponse getAircraftById(Long Id) throws Exception {
        Aircraft aircraft = aircraftRepository.findById(Id).orElseThrow(()->
                new Exception("Aircraft does not exists with id "+Id));
        return AircraftMapper.toResponse(aircraft);
    }

    @Override
    public List<AircraftResponse> listAllAircraftByOwner(Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(()->
                new Exception("Airline does not exists with ownerId"+ownerId));

        List<AircraftResponse> aircrafts = aircraftRepository.findByAirlineId(airline.getId())
                .stream().map(AircraftMapper::toResponse).toList();

        return aircrafts;
    }

    @Override
    public AircraftResponse updateAircraft(Long id,AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(()->
                new Exception("Airline does not exists with ownerId"+ownerId));

        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id,airline.getId());
        if(aircraft == null){
            throw new Exception("Aircraft does not exists with airlineId and aircraftId");
        }
        //new aircraft code should not be associated with
        //another aircraft in database

        if(request.getCode()!=null && !aircraft.getCode().equals(request.getCode()) &&
            aircraftRepository.existsByCode(aircraft.getCode())){
                throw new Exception("code already exists with another aircraft");
        }
        AircraftMapper.updateAircraft(request,aircraft,airline);
        return AircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Override
    public void deleteAircraft(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(()->
                new Exception("Airline does not exists with ownerId"+ownerId));

        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id,airline.getId());
        if(aircraft == null){
            throw new Exception("Aircraft does not exists with airlineId and aircraftId");
        }

        aircraftRepository.delete(aircraft);
    }
}
