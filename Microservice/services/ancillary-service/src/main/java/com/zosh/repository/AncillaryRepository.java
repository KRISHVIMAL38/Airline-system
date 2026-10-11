package com.zosh.repository;

import com.zosh.model.Ancillary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AncillaryRepository extends JpaRepository<Ancillary,Long> {
    List<Ancillary>  findByAirlineId(Long airlineId);
}
