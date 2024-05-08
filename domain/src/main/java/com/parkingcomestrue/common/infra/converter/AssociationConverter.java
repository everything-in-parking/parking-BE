package com.parkingcomestrue.common.infra.converter;

import com.parkingcomestrue.common.support.Association;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AssociationConverter implements AttributeConverter<Association, Long> {

    @Override
    public Long convertToDatabaseColumn(Association attribute) {
        return attribute.getId();
    }

    @Override
    public Association convertToEntityAttribute(Long dbData) {
        return Association.from(dbData);
    }
}
