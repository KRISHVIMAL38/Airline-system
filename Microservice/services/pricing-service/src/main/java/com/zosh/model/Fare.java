package com.zosh.model;

import com.zosh.embeddable.*;
import com.zosh.enums.CabinClassType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Fare {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Character rbdCode;

    @Column(nullable = false)
    private Long flightId;

    @Column(nullable = false)
    private Long cabinClassId;

    @Enumerated(EnumType.STRING)
    private CabinClassType cabinClass;

    @Column(nullable = false)
    private Double baseFare;

    private Double taxAndFees;
    private Double airlineFees;

    @Column(nullable = false)
    private Double currentPrice;

    private String fareLabel;

    @OneToOne(mappedBy = "fare",cascade = CascadeType.ALL,orphanRemoval = true)
    private BaggagePolicy baggagePolicy;

    @OneToOne(mappedBy = "fare",cascade = CascadeType.ALL,orphanRemoval = true)
    private FareRules fareRule;

    @Embedded
    private SeatBenefits seatBenefits = new SeatBenefits();

    @Embedded
    private BoardingBenefits boardingBenefits = new BoardingBenefits();

    @Embedded
    @Builder.Default
    private InFlightBenefits inFlightBenefits = new InFlightBenefits();

    @Embedded
    @Builder.Default
    private FlexibilityBenefits flexibilityBenefits = new FlexibilityBenefits();

    @Embedded
    @Builder.Default
    private PremiumServiceBenefits premiumServiceBenefits = new PremiumServiceBenefits();

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public Double getTotalPrice(){
        return baseFare+taxAndFees+currentPrice+airlineFees;
    }
}
