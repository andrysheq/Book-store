package moderation.user.usermoderationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import moderation.user.usermoderationservice.handler.UserHandler;
import moderation.user.usermoderationservice.model.contract.UserRegistryRequest;
import moderation.user.usermoderationservice.model.contract.UserView;
import moderation.user.usermoderationservice.security.RequireRole;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
@Tag(name = "Users", description = "Модерация пользователей")
public class UserController {

    private final UserHandler userHandler;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение пользователя по id")
    @RequireRole("MODERATOR")
    public UserView getUser(
            @Parameter(description = "Идентификатор пользователя")
            @NotNull
            @PathVariable("id")
            Long userId) {
        return userHandler.getUserById(userId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Изменение статуса пользователя")
    @RequireRole("MODERATOR")
    public UserView changeUserStatus(
            @Parameter(description = "Идентификатор пользователя")
            @NotNull
            @PathVariable("id")
            Long userId,
            @RequestParam(value = "status_id")
            @Parameter(description = "Идентификатор статуса")
            Integer statusId) {
        return userHandler.setUserStatus(userId, statusId);
    }

    @PostMapping("/catalog")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение каталога книг для модератора с фильтрацией")
    @RequireRole("MODERATOR")
    public Page<UserView> getUsers(
            @Valid @RequestBody UserRegistryRequest request,
            @ParameterObject
            Pageable pageable) {
        return userHandler.getUsers(request, pageable);
    }
}
