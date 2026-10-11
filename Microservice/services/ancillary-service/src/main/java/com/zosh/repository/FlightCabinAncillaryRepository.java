package com.zosh.repository;

import com.zosh.enums.AncillaryType;
import com.zosh.model.FlightCabinAncillary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightCabinAncillaryRepository extends JpaRepository<FlightCabinAncillary, Long> {
    List<FlightCabinAncillary> findByFlightIdAndCabinClassId(Long flightId,Long cabinClassId);

    FlightCabinAncillary findByFlightIdAndCabinClassIdAndAncillaryType(
            Long flightId, Long cabinClassId, AncillaryType ancillaryType);

    List<FlightCabinAncillary> findAllByFlightIdAndCabinClassIdAndAncillaryType(
            Long flightId, Long cabinClassId, AncillaryType ancillaryType);


}
