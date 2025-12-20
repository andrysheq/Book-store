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
    INAPPROPRIATE_CONTENT(1, "Книга запрещена на территории РФ"),
    COPYRIGHT_VIOLATION(2, "Нарушение авторских прав"),
    TECHNICAL_ISSUES(3, "Технические проблемы"),
    POLICY_VIOLATION(4, "Нарушение политики платформы"),
    AUTHOR_REQUEST(5, "По запросу автора"),
    OTHER(6, "Другое");

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
