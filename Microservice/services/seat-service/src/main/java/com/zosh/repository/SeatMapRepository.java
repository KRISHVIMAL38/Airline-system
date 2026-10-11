package com.zosh.repository;

import com.zosh.model.SeatMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatMapRepository extends JpaRepository<SeatMap,Long> {
    SeatMap findByCabinClassId(Long cabinClassId);
    boolean existsByAirlineIdAndCabinClassIdAndName(Long airlineId,Long cabinClassId,String name);
}
