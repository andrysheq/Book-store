package com.example.library.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Причины блокировки книги модератором
 */
@RequiredArgsConstructor
@Getter
public enum BookBlockingReasonEnum {
    INAPPROPRIATE_CONTENT(1, "Неприемлемое содержание"),
    COPYRIGHT_VIOLATION(2, "Нарушение авторских прав"),
    SPAM(3, "Спам"),
    DUPLICATED_ENTRY(4, "Дублирующаяся запись"),
    TECHNICAL_ISSUES(5, "Технические проблемы"),
    POLICY_VIOLATION(6, "Нарушение политики платформы"),
    AUTHOR_REQUEST(7, "По запросу автора"),
    OTHER(8, "Другое");

    private final Integer id;
    private final String title;

    public static BookBlockingReasonEnum of(Integer id) {
        return Stream.of(BookBlockingReasonEnum.values())
                .filter(e -> Objects.equals(e.id, id))
                .findFirst()
                .orElse(null);
    }

    public static BookBlockingReasonEnum ofTitle(String title) {
        return Stream.of(BookBlockingReasonEnum.values())
                .filter(e -> Objects.equals(e.title, title))
                .findFirst()
                .orElse(null);
    }
}
