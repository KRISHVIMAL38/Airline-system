package com.zosh.service;

import com.zosh.domain.AncillaryMetadata;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import tools.jackson.databind.ObjectMapper;

@Convert
public class AncillaryMetadataConverter implements AttributeConverter<AncillaryMetadata, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(AncillaryMetadata ancillaryMetadata) {
        if (ancillaryMetadata == null) return null;
        return objectMapper.writeValueAsString(ancillaryMetadata);
    }

    @Override
    public AncillaryMetadata convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return objectMapper.readValue(dbData, AncillaryMetadata.class);
    }
}
