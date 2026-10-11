package com.zosh.repository;

import com.zosh.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {
    boolean existsBySeatMapId(Long seatMapId);
    List<Seat> findBySeatMapId(Long seatMapId);
}
