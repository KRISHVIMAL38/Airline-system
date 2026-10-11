package com.zosh.service.impl;

import com.zosh.enums.SeatAvailabilityStatus;
import com.zosh.enums.SeatType;
import com.zosh.mapper.FlightInstanceCabinMapper;
import com.zosh.model.CabinClass;
import com.zosh.model.FlightInstanceCabin;
import com.zosh.model.SeatInstance;
import com.zosh.model.SeatMap;
import com.zosh.payload.request.FlightInstanceCabinRequest;
import com.zosh.payload.response.FlightInstanceCabinResponse;
import com.zosh.repository.CabinClassRepository;
import com.zosh.repository.FlightInstanceCabinRepository;
import com.zosh.repository.SeatInstanceRepository;
import com.zosh.repository.SeatMapRepository;
import com.zosh.service.FlightInstanceCabinService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightInstanceCabinServiceImpl implements FlightInstanceCabinService {

    @Autowired
    private CabinClassRepository cabinClassRepository;

    @Autowired
    private SeatMapRepository seatMapRepository;

    @Autowired
    private FlightInstanceCabinRepository flightInstanceCabinRepository;

    @Autowired
    private SeatInstanceRepository seatInstanceRepository;

    @Override
    public FlightInstanceCabinResponse createFlightInstanceCabin(FlightInstanceCabinRequest request) throws Exception {
        CabinClass cabinClass = cabinClassRepository.findById(request.getCabinClassId())
                .orElseThrow(()-> new Exception("Cabin Class not found with id"+request.getCabinClassId()));

        SeatMap seatMap =seatMapRepository.findByCabinClassId(cabinClass.getId());

        if(seatMap == null){
            throw new Exception("Seat map not found");
        }

        if(seatMap.getSeats() == null || seatMap.getSeats().isEmpty()){
            throw new Exception("No seats found in the seat map");
        }

        int totalSeats = seatMap.getSeats().size();

        FlightInstanceCabin flightInstanceCabin = FlightInstanceCabin.builder()
                .flightInstanceId(request.getFlightInstanceId())
                .cabinClass(cabinClass)
                .totalSeats(totalSeats)
                .bookedSeats(0)
                .build();

        FlightInstanceCabin saved = flightInstanceCabinRepository.save(flightInstanceCabin);

        List<SeatInstance> seatInstances = seatMap.getSeats().stream()
                .map(
                        seat -> {
                            Double premiumSuperCharge = getPremiumSuperCharge(
                                    seat.getSeatType(),
                                    1000.0,
                                    500.0
                            );
                            SeatInstance seatInstance =  SeatInstance.builder()
                                    .flightId(request.getFlightId())
                                    .status(SeatAvailabilityStatus.AVAILABLE)
                                    .flightInstanceId(request.getFlightInstanceId())
                                    .flightInstanceCabin(flightInstanceCabin)
                                    .seat(seat)
                                    .isAvailable(true)
                                    .isBooked(false)
                                    .premiumSuperCharge(premiumSuperCharge)
                                    .build();

                            return seatInstance;
                        }
                ).toList();

        seatInstanceRepository.saveAll(seatInstances);

        saved.setSeats(seatInstances);
        return FlightInstanceCabinMapper.toResponse(saved);
    }

    @Override
    public FlightInstanceCabinResponse getFlightInstanceCabin(Long id) throws Exception {
        FlightInstanceCabin fic = flightInstanceCabinRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("flight Instance Cabin Not found with Id = "+id));


        return FlightInstanceCabinMapper.toResponse(fic);
    }

    @Override
    public Page<FlightInstanceCabinResponse> getByFlightInstanceId(Long id, Pageable pageable) {
        return flightInstanceCabinRepository.findByFlightInstanceId(id, pageable)
                .map(FlightInstanceCabinMapper::toResponse);
    }

    @Override
    public FlightInstanceCabinResponse getByFlightInstanceAndCabinClassId(Long flightInstanceId, Long cabinClassId) {
        FlightInstanceCabin fic = flightInstanceCabinRepository.findByFlightInstanceIdAndCabinClassId(
                flightInstanceId,
                cabinClassId);

        return FlightInstanceCabinMapper.toResponse(fic);
    }

    @Override
    public FlightInstanceCabinResponse updateFlightInstanceCabin(Long id, FlightInstanceCabinRequest request) {
        FlightInstanceCabin fic = flightInstanceCabinRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("flight Instance Cabin Not found with Id = "+id));

        //Imp throw this exception everywhere
        if(request.getCabinClassId()!=null){
            CabinClass cabinClass = cabinClassRepository.findById(request.getCabinClassId())
                    .orElseThrow(()->new EntityNotFoundException("cabin class not found"));

            fic.setCabinClass(cabinClass);
        }

        FlightInstanceCabin updated = flightInstanceCabinRepository.save(fic);
        return FlightInstanceCabinMapper.toResponse(updated);
    }

    @Override
    public void deleteFlightInstanceCabin(Long id) {
        FlightInstanceCabin fic = flightInstanceCabinRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("flight Instance Cabin Not found with Id = "+id));

        flightInstanceCabinRepository.delete(fic);
    }

    private Double getPremiumSuperCharge(SeatType seatType,
                                         Double windowSuperCharge,
                                         Double aisleSupercharge){
            if(seatType == null)return 0.0;

            return switch (seatType){
                case AISLE -> aisleSupercharge;
                case WINDOW ->  windowSuperCharge;
                default -> 0.0;
            };
    }
}
