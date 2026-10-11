package com.zosh.service.impl;

import com.zosh.client.AirlineClient;
import com.zosh.client.LocationClient;
import com.zosh.enums.FlightStatus;
import com.zosh.mapper.FlightInstanceMapper;
import com.zosh.mapper.FlightScheduleMapper;
import com.zosh.model.Flight;
import com.zosh.model.FlightSchedule;
import com.zosh.payload.request.FlightInstanceRequest;
import com.zosh.payload.request.FlightScheduleRequest;
import com.zosh.payload.response.AircraftResponse;
import com.zosh.payload.response.AirlineResponse;
import com.zosh.payload.response.AirportResponse;
import com.zosh.payload.response.FlightScheduleResponse;
import com.zosh.repository.FlightInstanceRepository;
import com.zosh.repository.FlightRepository;
import com.zosh.repository.FlightScheduleRepository;
import com.zosh.service.FlightInstanceService;
import com.zosh.service.FlightScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightScheduleServiceImpl implements FlightScheduleService {
    @Autowired
    private FlightScheduleRepository scheduleRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightInstanceService instanceService;

    @Autowired
    private AirlineClient airlineClient;

    @Autowired
    private LocationClient locationClient;

    @Override
    public FlightScheduleResponse createFlightSchedule(Long userId, FlightScheduleRequest request) throws Exception {

        AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);
        Flight flight = flightRepository.findById(request.getFlightId()).orElseThrow(()->
                new Exception("Flight Not found with id  = "+request.getFlightId()));

        if(request.getEndDate().isBefore(request.getStartDate())){
            throw new Exception("end date is before start date");
        }
        FlightSchedule flightSchedule = FlightScheduleMapper.toSchedule(request,flight);
        FlightSchedule savedSchedule = scheduleRepository.save(flightSchedule);

        List<DayOfWeek> operatingDays = savedSchedule.getOperatingDays();
        LocalDate startDate = savedSchedule.getStartDate();
        LocalDate endDate = savedSchedule.getEndDate();

        FlightInstanceRequest flightInstanceRequest = FlightInstanceRequest.builder()
                .scheduleId(savedSchedule.getId())
                .flightId(flight.getId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureAirportId(flight.getDepartureAirportId())
                .status(FlightStatus.SCHEDULED)
                .build();

        for(LocalDate date = startDate; !date.isAfter(endDate);date=date.plusDays(1)){
            if(operatingDays.contains(date.getDayOfWeek())){
                flightInstanceRequest.setDepartureDateTime(
                        LocalDateTime.of(date,savedSchedule.getDepartureTime())
                );
                flightInstanceRequest.setArrivalDateTime(
                        LocalDateTime.of(date,savedSchedule.getArrivalTime())
                );
                flightInstanceRequest.setAirlineId(airlineResponse.getId());
                instanceService.createFlightInstance(airlineResponse.getId(),flightInstanceRequest);
            }
        }
        return convertToFlightScheduleResponse(flightSchedule);
    }

    @Override
    public FlightScheduleResponse getFlightScheduleById(Long Id) throws Exception {
        FlightSchedule schedule = scheduleRepository.findById(Id).orElseThrow(()->
                new Exception("Flight schedule not found with id"+Id));
        return convertToFlightScheduleResponse(schedule);
    }

    @Override
    public List<FlightScheduleResponse> getFlightScheduleByAirline(Long userId) {
        AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);
        List<FlightSchedule> flightSchedules = scheduleRepository.findByFlightAirlineId(airlineResponse.getId());

        return flightSchedules.stream().map(this::convertToFlightScheduleResponse).toList();
    }

    @Override
    public FlightScheduleResponse updateFlightSchedule(Long Id, FlightScheduleRequest request) throws Exception {
        FlightSchedule existing = scheduleRepository.findById(Id).orElseThrow(()->
                new Exception("Flight schedule not found with id"+Id));

        FlightScheduleMapper.updateSchedule(request,existing);
        FlightSchedule saved = scheduleRepository.save(existing);

        return convertToFlightScheduleResponse(saved);
    }

    @Override
    public void deleteFlightScheduleById(Long Id) throws Exception {
        FlightSchedule existing = scheduleRepository.findById(Id).orElseThrow(()->
                new Exception("Flight schedule not found with id"+Id));

        scheduleRepository.delete(existing);
    }

    private FlightScheduleResponse convertToFlightScheduleResponse(FlightSchedule flightSchedule){

        AirportResponse departureAirport = locationClient
                .getAirportById(flightSchedule.getDepartureAirportId());

        AirportResponse arrivalAirport = locationClient
                .getAirportById(flightSchedule.getArrivalAirportId());

        return FlightScheduleMapper.toResponse(flightSchedule,arrivalAirport,departureAirport);

    }
}
