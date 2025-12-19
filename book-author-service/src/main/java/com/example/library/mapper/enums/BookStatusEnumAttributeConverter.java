package com.example.library.mapper.enums;

import com.example.library.model.enums.BookStatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter для BookStatusEnum в базе данных
 */
@Converter(autoApply = true)
public class BookStatusEnumAttributeConverter implements AttributeConverter<BookStatusEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BookStatusEnum attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public BookStatusEnum convertToEntityAttribute(Integer dbData) {
        return BookStatusEnum.of(dbData);
    }
}
