package com.example.library.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Причины отклонения рецензии модератором
 */
@RequiredArgsConstructor
@Getter
public enum ReviewRejectionReasonEnum {
    SPAM(1, "Спам"),
    OFFENSIVE_CONTENT(2, "Оскорбительный контент"),
    IRRELEVANT_CONTENT(3, "Содержание не относится к книге"),
    INAPPROPRIATE_LANGUAGE(4, "Ненормативная лексика"),
    OTHER(5, "Другое");

    private final Integer id;
    private final String title;

    public static ReviewRejectionReasonEnum of(Integer id) {
        return Stream.of(ReviewRejectionReasonEnum.values())
                .filter(e -> Objects.equals(e.id, id))
                .findFirst()
                .orElse(null);
    }

    public static ReviewRejectionReasonEnum ofTitle(String title) {
        return Stream.of(ReviewRejectionReasonEnum.values())
                .filter(e -> Objects.equals(e.title, title))
                .findFirst()
                .orElse(null);
    }
}
