package com.zosh.repository;

import com.zosh.model.FlightInstanceCabin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightInstanceCabinRepository extends JpaRepository<FlightInstanceCabin,Long>{
    Page<FlightInstanceCabin> findByFlightInstanceId(Long flightInstanceId, Pageable pageable);
    FlightInstanceCabin findByFlightInstanceIdAndCabinClassId(Long flightInstanceId, Long cabinClassId);

}
