package com.zosh.model;

import com.zosh.embeddable.Support;
import com.zosh.enums.AirlineStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true , nullable = false)
    private String iataCode;

    @Column(unique = true , nullable = false)
    private String icaoCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long ownerId;

    private String alias;
    private String logoUrl;
    private String website;
    @Enumerated(EnumType.STRING)
    private AirlineStatus status = AirlineStatus.ACTIVE;

    private String alliance;
    @Embedded
    private Support support;

    private Long headquarterCityId;
    private Long updatedById;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;


}
