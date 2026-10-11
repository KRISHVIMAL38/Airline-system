package com.zosh.repository;

import com.zosh.model.Aircraft;
import com.zosh.payload.response.AircraftResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft,Long> {
    List<Aircraft> findByAirlineId(Long airlineId);
    Boolean existsByCode(String code);
    Boolean existsByCodeAndIdNot(String code,Long Id);
    Aircraft findByIdAndAirlineId(Long Id, Long airlineId);
}
