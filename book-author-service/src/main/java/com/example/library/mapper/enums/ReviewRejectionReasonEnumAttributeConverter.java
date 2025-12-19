package com.example.library.mapper.enums;

import com.example.library.model.enums.ReviewRejectionReasonEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter для ReviewRejectionReasonEnum в базе данных
 */
@Converter(autoApply = true)
public class ReviewRejectionReasonEnumAttributeConverter implements AttributeConverter<ReviewRejectionReasonEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ReviewRejectionReasonEnum attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public ReviewRejectionReasonEnum convertToEntityAttribute(Integer dbData) {
        return ReviewRejectionReasonEnum.of(dbData);
    }
}
