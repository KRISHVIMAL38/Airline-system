package com.zosh.repository;

import com.zosh.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {
    @Query("""
        select f from Flight f
        where f.airlineId = :airlineId
        and (:deptId is null or f.departureAirportId = :deptId)
        and (:arrId is null or f.arrivalAirportId = :arrId )
        """)
    Page<Flight> findByAirlineId(@Param("airlineId") Long airlineId,
                                 @Param("deptId") Long deptId,
                                 @Param("arrId") Long arrId,
                                 Pageable pageable);
    Boolean existsByFlightNumber(String flightNumber);
    Boolean existsByFlightNumberAndIdNot(String flightNumber, Long Id);
    Optional<Flight> findByAirlineIdAndId(Long airlineId, Long Id);
}
