package com.zosh.service;

import com.zosh.enums.AncillaryType;
import com.zosh.payload.request.FlightCabinAncillaryRequest;
import com.zosh.payload.response.FlightCabinAncillaryResponse;

import java.util.List;

public interface FlightCabinAncillaryService {
    FlightCabinAncillaryResponse createFlightCabinAncillary(FlightCabinAncillaryRequest request);
    FlightCabinAncillaryResponse getById(Long id);
    List<FlightCabinAncillaryResponse> getByFlightAndCabinClass(Long flightId, Long cabinClassId);
    List<FlightCabinAncillaryResponse> getAllByIds(List<Long> ids);
    FlightCabinAncillaryResponse getByFlightIdAndCabinClassIdAndType(
            Long flightId, Long cabinClassId, AncillaryType type);
    List<FlightCabinAncillaryResponse> getAllByFlightIdAndCabinClassIdAndType(
            Long flightId, Long cabinClassId, AncillaryType type);
    FlightCabinAncillaryResponse updateFlightCabinAncillary(Long id, FlightCabinAncillaryRequest request);
    void deleteFlightCabinAncillary(Long id);
    Double calculateAncillaryPrice(List<Long> ancillaryIds);

}
