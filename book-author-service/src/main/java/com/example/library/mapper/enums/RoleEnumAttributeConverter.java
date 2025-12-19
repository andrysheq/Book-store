package com.example.library.mapper.enums;

import com.example.library.model.enums.RoleEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter для RoleEnum в базе данных
 */
@Converter(autoApply = true)
public class RoleEnumAttributeConverter implements AttributeConverter<RoleEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(RoleEnum attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public RoleEnum convertToEntityAttribute(Integer dbData) {
        return RoleEnum.of(dbData);
    }
}
