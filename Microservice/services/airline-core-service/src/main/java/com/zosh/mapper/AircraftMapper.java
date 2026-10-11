package com.zosh.mapper;

import com.zosh.model.Aircraft;
import com.zosh.model.Airline;
import com.zosh.payload.request.AircraftRequest;
import com.zosh.payload.response.AircraftResponse;

public class AircraftMapper {
    public static Aircraft toAircraft(AircraftRequest request, Airline airline){
        if(request == null)return null;

        return Aircraft.builder()
                .code(request.getCode())
                .model(request.getModel())
                .manufacture(request.getManufacture())
                .seatingCapacity(request.getSeatingCapacity())
                .economySeats(request.getEconomySeats())
                .premiumEconomySeats(request.getPremiumEconomySeats())
                .businessSeats(request.getBusinessSeats())
                .firstClassSeats(request.getFirstClassSeats())
                .rangeKm(request.getRangeKm())
                .cruisingSpeedKmh(request.getCruisingSpeedKmh())
                .maxAltitude(request.getMaxAltitude())
                .yearOfManufacture(request.getYearOfManufacture())
                .registrationDate(request.getRegistrationDate())
                .nextMaintenanceDate(request.getNextMaintenanceDate())
                .status(request.getStatus())
                .isAvailable(request.getIsAvailable())
                .airline(airline)
                .currenAirportId(request.getCurrentAirportId())
                .build();
    }

    public static AircraftResponse toResponse(Aircraft aircraft){
        if(aircraft == null)return null;
        return AircraftResponse.builder()
                .id(aircraft.getId())
                .code(aircraft.getCode())
                .model(aircraft.getModel())
                .manufacture(aircraft.getManufacture())
                .seatingCapacity(aircraft.getSeatingCapacity())
                .economySeats(aircraft.getEconomySeats())
                .premiumEconomySeats(aircraft.getPremiumEconomySeats())
                .businessSeats(aircraft.getBusinessSeats())
                .firstClassSeats(aircraft.getFirstClassSeats())
                .rangeKm(aircraft.getRangeKm())
                .cruisingSpeedKmh(aircraft.getCruisingSpeedKmh())
                .maxAltitude(aircraft.getMaxAltitude())
                .yearOfManufacture(aircraft.getYearOfManufacture())
                .registrationDate(aircraft.getRegistrationDate())
                .nextMaintenanceDate(aircraft.getNextMaintenanceDate())
                .status(aircraft.getStatus())
                .isAvailable(aircraft.getIsAvailable())
                .airlineId(aircraft.getAirline()!=null ? aircraft.getAirline().getId():null)
                .airlineName(aircraft.getAirline()!=null ? aircraft.getAirline().getName():null)
                .airlineIataCode(aircraft.getAirline()!=null ? aircraft.getAirline().getIataCode():null)
                .currentAirportId(aircraft.getCurrenAirportId())
                .totalSeats(aircraft.getTotalSeats())
                .requiresMaintenance(aircraft.getRequiresMaintenance()!=null ? aircraft.getRequiresMaintenance():false)
                .isOperational(aircraft.isOperational())
                .createdAt(aircraft.getCreatedAt())
                .updatedAt(aircraft.getUpdatedAt())
                .build();
    }

    public static void updateAircraft(AircraftRequest request,Aircraft aircraft,Airline airline){
        if(request == null)return;

        aircraft.setCode(request.getCode());
        aircraft.setModel(request.getModel());
        aircraft.setManufacture(request.getManufacture());
        aircraft.setSeatingCapacity(request.getSeatingCapacity());
        aircraft.setEconomySeats(request.getEconomySeats()) ;
        aircraft.setPremiumEconomySeats(request.getPremiumEconomySeats());
        aircraft.setBusinessSeats(request.getBusinessSeats());
        aircraft.setFirstClassSeats(request.getFirstClassSeats());
        aircraft.setRangeKm(request.getRangeKm());
        aircraft.setCruisingSpeedKmh(request.getCruisingSpeedKmh());
        aircraft.setMaxAltitude(request.getMaxAltitude());
        aircraft.setYearOfManufacture(request.getYearOfManufacture());
        aircraft.setRegistrationDate(request.getRegistrationDate());
        aircraft.setNextMaintenanceDate(request.getNextMaintenanceDate());
        aircraft.setStatus(request.getStatus());
        aircraft.setIsAvailable(request.getIsAvailable());
        aircraft.setAirline(airline);
        aircraft.setCurrenAirportId(request.getCurrentAirportId());
    }
}
