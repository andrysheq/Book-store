package com.example.library.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Роли пользователей в системе
 */
@RequiredArgsConstructor
@Getter
public enum RoleEnum {
    ADMIN(1, "ADMIN"),
    USER(2, "USER"),
    MODERATOR(3, "MODERATOR");

    private final Integer id;
    private final String code;

    public static RoleEnum of(Integer id) {
        return Stream.of(RoleEnum.values())
                .filter(e -> Objects.equals(e.id, id))
                .findFirst()
                .orElse(null);
    }

    public static RoleEnum ofCode(String code) {
        return Stream.of(RoleEnum.values())
                .filter(e -> Objects.equals(e.code, code))
                .findFirst()
                .orElse(null);
    }
}