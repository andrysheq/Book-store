package com.example.library.usersservice.controller;

import com.example.library.usersservice.model.AuthRequest;
import com.example.library.usersservice.model.AuthResponse;
import com.example.library.usersservice.model.RegisterRequest;
import com.example.library.usersservice.model.UserResponse;
import com.example.library.usersservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST контроллер для аутентификации и авторизации
 * Endpoints:
 * - POST /auth/register - Регистрация
 * - POST /auth/login - Авторизация
 * - GET /auth/me - Получить текущего пользователя
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Регистрация нового пользователя
     *
     * POST /auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * Авторизация пользователя (вход в систему)
     *
     * POST /auth/login
     * Response: { "token": "...", "user": {...} }
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Получить информацию о текущем пользователе
     *
     * GET /auth/me
     * Header: Authorization: Bearer <token>
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            @RequestHeader("Authorization") String authHeader) {

        // Извлечь токен из заголовка
        String token = extractTokenFromHeader(authHeader);

        UserResponse user = authService.getCurrentUser(token);
        return ResponseEntity.ok(user);
    }

    /**
     * Изменить пароль пользователя
     *
     * PATCH /auth/change-password
     * Header: Authorization: Bearer <token>
     */
    @PatchMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody ChangePasswordRequest request) {

        // Извлечь токен и email
        String token = extractTokenFromHeader(authHeader);
        UserResponse user = authService.getCurrentUser(token);

        authService.changePassword(user.email(), request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.noContent().build();
    }

    /**
     * Извлечь JWT токен из заголовка Authorization
     * Формат: "Bearer <token>"
     */
    private String extractTokenFromHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Отсутствует токен в заголовке Authorization");
        }
        return authHeader.substring("Bearer ".length());
    }

    /**
     * DTO для изменения пароля
     */
    public static class ChangePasswordRequest {
        public String oldPassword;
        public String newPassword;

        public String getOldPassword() {
            return oldPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }
    }
}
