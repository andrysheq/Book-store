package com.example.library.mapper.enums;

import com.example.library.model.enums.BookBlockingReasonEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter для BookBlockingReasonEnum в базе данных
 */
@Converter(autoApply = true)
public class BookBlockingReasonEnumAttributeConverter implements AttributeConverter<BookBlockingReasonEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BookBlockingReasonEnum attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public BookBlockingReasonEnum convertToEntityAttribute(Integer dbData) {
        return BookBlockingReasonEnum.of(dbData);
    }
}
