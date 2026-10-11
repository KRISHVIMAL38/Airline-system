package com.zosh.model;


import com.zosh.enums.AncillaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class FlightCabinAncillary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long flightId;

    @Column(nullable = false)
    private Long cabinClassId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ancillary ancillary;

    private Boolean available;

    private Integer maxQuantity;

    private Double price;

    private Boolean includedInFare = false;
    private AncillaryType type;

}
