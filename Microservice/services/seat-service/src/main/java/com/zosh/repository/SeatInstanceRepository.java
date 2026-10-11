package com.zosh.repository;

import com.zosh.model.SeatInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatInstanceRepository extends JpaRepository<SeatInstance, Long> {
}
