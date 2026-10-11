package com.zosh.repository;

import com.zosh.enums.CabinClassType;
import com.zosh.model.CabinClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CabinClassRepository extends JpaRepository<CabinClass,Long> {
    List<CabinClass> findByAircraftId(Long aircraftId);
    CabinClass findByAircraftIdAndName(Long aircraftId, CabinClassType name);
    boolean existsByCodeAndAircraftId(String code,Long aircraftId);
    boolean existsByCodeAndAircraftIdAndIdNot(String code,Long aircraftId,Long id);
}
