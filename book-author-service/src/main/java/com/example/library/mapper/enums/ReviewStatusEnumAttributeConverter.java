package com.example.library.mapper.enums;

import com.example.library.model.enums.ReviewStatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter для ReviewStatusEnum в базе данных
 */
@Converter(autoApply = true)
public class ReviewStatusEnumAttributeConverter implements AttributeConverter<ReviewStatusEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ReviewStatusEnum attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public ReviewStatusEnum convertToEntityAttribute(Integer dbData) {
        return ReviewStatusEnum.of(dbData);
    }
}
