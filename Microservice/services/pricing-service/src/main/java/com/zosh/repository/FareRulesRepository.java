package com.zosh.repository;

import com.zosh.model.FareRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FareRulesRepository extends JpaRepository<FareRules,Long> {
    FareRules findByFareId(Long fareId);
    List<FareRules> getFareRulesByAirlineId(Long airlineId);
    Boolean existsByFareId(Long fareId);
}
