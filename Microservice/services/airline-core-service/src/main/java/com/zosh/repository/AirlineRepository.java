package com.zosh.repository;

import com.zosh.enums.AirlineStatus;
import com.zosh.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirlineRepository extends JpaRepository<Airline,Long> {
    Optional<Airline> findByOwnerId(Long Id);

    List<Airline> findByStatus(AirlineStatus status);
}
