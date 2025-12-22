package moderation.user.usermoderationservice.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Статусы пользователя в системе
 */
@RequiredArgsConstructor
@Getter
public enum UserStatusEnum {
    ACTIVE(1, "Активен"),
    BLOCKED(2, "Заблокирован");

    private final Integer id;
    private final String title;

    public static UserStatusEnum of(Integer id) {
        return Stream.of(UserStatusEnum.values())
                .filter(e -> Objects.equals(e.id, id))
                .findFirst()
                .orElse(null);
    }

    public static UserStatusEnum ofTitle(String title) {
        return Stream.of(UserStatusEnum.values())
                .filter(e -> Objects.equals(e.title, title))
                .findFirst()
                .orElse(null);
    }
}

