package com.zosh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class SeatMap {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private int totalRows;

    @Column(nullable = false)
    private int rightSeatsPerRows;

    @Column(nullable = false)
    private int leftSeatsPerRows;

    @Column(nullable = false)
    private Long airlineId;

    @OneToMany(mappedBy = "seatMap",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Seat> seats;

    @OneToOne
    private CabinClass cabinClass;
}
