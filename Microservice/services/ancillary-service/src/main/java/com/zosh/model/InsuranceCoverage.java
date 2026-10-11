package com.zosh.model;

import com.zosh.enums.CoverageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
public class InsuranceCoverage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Ancillary ancillary;

    @Column(nullable = false)
    private CoverageType coverageType;

    @Column(nullable = false,length = 200)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(nullable = false)
    private Double coverageAmount;

    private Boolean active = true;
    private Boolean isFlat = true;
    private String claimCondition;
    private Integer displayOrder;
    private String emergencyContact;

}
