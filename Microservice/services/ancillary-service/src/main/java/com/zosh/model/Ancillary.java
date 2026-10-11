package com.zosh.model;

import com.zosh.domain.AncillaryMetadata;
import com.zosh.enums.AncillaryType;
import com.zosh.service.AncillaryMetadataConverter;
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
public class Ancillary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AncillaryType Type;

    private String subType;
    private String rfic;
    @Column(nullable = false)
    private String name;

    private String description;

    @Convert(converter = AncillaryMetadataConverter.class)
    private AncillaryMetadata ancillaryMetaData;

    private Integer displayOrder;
    private Long airlineId;
}
