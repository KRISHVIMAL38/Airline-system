package com.zosh.event;

import com.zosh.enums.SeatAvailabilityStatus;
import com.zosh.enums.SeatType;
import com.zosh.model.CabinClass;
import com.zosh.model.FlightInstanceCabin;
import com.zosh.model.Seat;
import com.zosh.model.SeatInstance;
import com.zosh.repository.CabinClassRepository;
import com.zosh.repository.FlightInstanceCabinRepository;
import com.zosh.repository.SeatInstanceRepository;
import com.zosh.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightInstanceEventConsumer {

    @Autowired
    private final CabinClassRepository cabinClassRepository;

    @Autowired
    private final SeatRepository seatRepository;

    @Autowired
    private final FlightInstanceCabinRepository flightInstanceCabinRepository;

    @Autowired
    private final SeatInstanceRepository seatInstanceRepository;

    @KafkaListener(topics = "flight-instance-created",groupId = "seat-service-group")
    @Transactional
    public void handleFlightInstanceCreatedEvent(FlightInstanceCreatedEvent event) {
        List<CabinClass> cabinClasses = cabinClassRepository.findByAircraftId(event.getAircraftId());

        int totalSeatInstances =0;
        for (CabinClass cabinClass : cabinClasses) {
            List<Seat> seats = cabinClass.getSeatMap()!=null?
                    seatRepository.findBySeatMapId(cabinClass.getSeatMap().getId())
                    :List.of();

            FlightInstanceCabin flightInstanceCabin = FlightInstanceCabin.builder()
                    .flightInstanceId(event.getFlightInstanceId())
                    .cabinClass(cabinClass)
                    .totalSeats(seats.size())
                    .bookedSeats(0)
                    .build();

            FlightInstanceCabin saved = flightInstanceCabinRepository.save(flightInstanceCabin);

            List<SeatInstance> seatInstances = seats.stream().map(
                    seat -> SeatInstance.builder()
                            .flightId(event.getFlightId())
                            .flightInstanceId(event.getFlightInstanceId())
                            .flightInstanceCabin(saved)
                            .seat(seat)
                            .status(SeatAvailabilityStatus.AVAILABLE)
                            .isAvailable(true)
                            .isBooked(false)
                            .premiumSuperCharge(getPremiumSuperCharge(
                                    seat.getSeatType(),
                                    1000.0,
                                    500.0
                            ))
                            .build()).toList();

            seatInstanceRepository.saveAll(seatInstances);
            totalSeatInstances+=seatInstances.size();
        }
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
