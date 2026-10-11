package com.zosh.repository;

import com.zosh.model.BaggagePolicy;
import com.zosh.payload.response.BaggagePolicyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaggagePolicyRepository extends JpaRepository<BaggagePolicy,Long> {
    BaggagePolicy findByFareId(Long fareId);
    List<BaggagePolicy> findByAirlineId(Long airlineId);
    Boolean existsByFareId(Long fareId);
}
