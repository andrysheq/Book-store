package com.example.library.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Статусы книг в системе
 */
@RequiredArgsConstructor
@Getter
public enum BookStatusEnum {
    AVAILABLE(1, "В наличии"),
    BLOCKED(2, "Заблокирована");

    private final Integer id;
    private final String title;

    public static BookStatusEnum of(Integer id) {
        return Stream.of(BookStatusEnum.values())
                .filter(e -> Objects.equals(e.id, id))
                .findFirst()
                .orElse(null);
    }

    public static BookStatusEnum ofTitle(String title) {
        return Stream.of(BookStatusEnum.values())
                .filter(e -> Objects.equals(e.title, title))
                .findFirst()
                .orElse(null);
    }
}
