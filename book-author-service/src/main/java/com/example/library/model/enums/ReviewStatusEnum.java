package com.example.library.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Статусы рецензий на модерации
 */
@RequiredArgsConstructor
@Getter
public enum ReviewStatusEnum {
    ON_REVIEW(1, "На рассмотрении"),
    APPROVED(2, "Подтвержден"),
    REJECTED(3, "Отклонен");

    private final Integer id;
    private final String title;

    public static ReviewStatusEnum of(Integer id) {
        return Stream.of(ReviewStatusEnum.values())
                .filter(e -> Objects.equals(e.id, id))
                .findFirst()
                .orElse(null);
    }

    public static ReviewStatusEnum ofTitle(String title) {
        return Stream.of(ReviewStatusEnum.values())
                .filter(e -> Objects.equals(e.title, title))
                .findFirst()
                .orElse(null);
    }
}
