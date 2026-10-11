package com.zosh.service.impl;

import com.zosh.client.AirlineClient;
import com.zosh.mapper.SeatMapMapper;
import com.zosh.model.CabinClass;
import com.zosh.model.SeatMap;
import com.zosh.payload.request.SeatMapRequest;
import com.zosh.payload.response.AirlineResponse;
import com.zosh.payload.response.SeatMapResponse;
import com.zosh.repository.CabinClassRepository;
import com.zosh.repository.SeatMapRepository;
import com.zosh.repository.SeatRepository;
import com.zosh.service.SeatMapService;
import com.zosh.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatMapServiceImpl implements SeatMapService {
    @Autowired
    CabinClassRepository cabinClassRepository;

    @Autowired
    SeatMapRepository seatMapRepository;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    private SeatService seatService;

    @Autowired
    private AirlineClient airlineClient;

    @Override
    public SeatMapResponse creatSeatMap(Long userId, SeatMapRequest request) throws Exception {
        AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);
        CabinClass cabinClass = cabinClassRepository.findById(request.getCabinClassId())
                .orElseThrow(()->
                        new Exception("cabin class not found with the given Id")
                        );

        if(seatMapRepository.existsByAirlineIdAndCabinClassIdAndName(
                airlineResponse.getId(), request.getCabinClassId(), request.getName()
        )){
            throw new Exception("cabin class already exists with given name");
        }

        SeatMap seatMap = SeatMapMapper.toSeatMap(request,cabinClass);
        seatMap.setAirlineId(airlineResponse.getId());
        SeatMap saved = seatMapRepository.save(seatMap);

        seatService.generateSeats(saved.getId());
        seatMap.setSeats(seatRepository.findBySeatMapId(saved.getId()));
        return SeatMapMapper.toResponse(saved);
    }

    @Override
    public SeatMapResponse getSeatMapById(Long id) throws Exception {
        SeatMap seatMap =seatMapRepository.findById(id).orElseThrow(
                ()->new Exception("Seat Map not found with given id"));

        return SeatMapMapper.toResponse(seatMap);
    }

    @Override
    public SeatMapResponse getSeatMapByCabinClassId(Long cabinClassId) {
        SeatMap seatMap = seatMapRepository.findByCabinClassId(cabinClassId);
        return  SeatMapMapper.toResponse(seatMap);
    }

    @Override
    public SeatMapResponse updateSeatMap(Long id, SeatMapRequest request) throws Exception {
        SeatMap existing =seatMapRepository.findById(id).orElseThrow(
                ()->new Exception("Seat Map not found with given id"));

        SeatMapMapper.updateEntity(request,existing);
        SeatMap updated = seatMapRepository.save(existing);
        return SeatMapMapper.toResponse(updated);
    }

    @Override
    public void deleteSeatMap(Long id) throws Exception {
        SeatMap existing =seatMapRepository.findById(id).orElseThrow(
                ()->new Exception("Seat Map not found with given id"));
        seatMapRepository.delete(existing);
    }
}
