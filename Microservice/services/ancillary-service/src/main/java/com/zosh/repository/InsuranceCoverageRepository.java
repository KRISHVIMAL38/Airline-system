package com.zosh.repository;

import com.zosh.model.InsuranceCoverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceCoverageRepository extends JpaRepository<InsuranceCoverage, Long> {
    List<InsuranceCoverage> findByAncillaryId(Long ancillary);
    List<InsuranceCoverage> findByAncillaryIdAndActiveTrue(Long ancillary);
}
