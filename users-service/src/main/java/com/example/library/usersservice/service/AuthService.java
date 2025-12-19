package com.example.library.usersservice.service;

import com.example.library.usersservice.entity.UserEntity;
import com.example.library.usersservice.exception.BadRequestException;
import com.example.library.usersservice.exception.NotFoundException;
import com.example.library.usersservice.model.AuthRequest;
import com.example.library.usersservice.model.AuthResponse;
import com.example.library.usersservice.model.RegisterRequest;
import com.example.library.usersservice.model.UserResponse;
import com.example.library.usersservice.model.event.UserCreatedEvent;
import com.example.library.usersservice.repository.UserRepository;
import com.example.library.usersservice.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для аутентификации и авторизации пользователей
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    /**
     * Регистрация нового пользователя
     */
    @Transactional
    public UserResponse register(RegisterRequest request) {
        // Проверить, что пользователь не существует
        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException(
                    "Пользователь с email " + request.email() + " уже существует",
                    "EMAIL_ALREADY_EXISTS"
            );
        }

        // Валидировать пароль
        validatePassword(request.password());

        // Создать новую сущность пользователя
        UserEntity user = new UserEntity();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setRole("USER");  // По умолчанию обычный пользователь
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());

        // Сохранить в БД
        UserEntity savedUser = userRepository.save(user);

//        UserCreatedEvent event = UserCreatedEvent.builder()
//                .userId(savedUser.getId())
//                .email(savedUser.getEmail())
//                .firstName(savedUser.getFirstName())
//                .isActive(savedUser.getIsActive())
//                .build();
//
//        kafkaTemplate.send("user.created", user.getEmail(), event);

        return toUserResponse(savedUser);
    }

    /**
     * Авторизация пользователя (вход)
     */
    @Transactional
    public AuthResponse login(AuthRequest request) {
        // Найти пользователя по email
        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new NotFoundException(
                        "Пользователь не найден"));

        // Проверить, активен ли пользователь
        if (!user.getIsActive()) {
            throw new BadRequestException(
                    "Аккаунт пользователя деактивирован",
                    "ACCOUNT_DISABLED"
            );
        }

        // Проверить пароль
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadRequestException(
                    "Неправильный пароль",
                    "INVALID_PASSWORD"
            );
        }

        // Обновить время последнего входа
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        // Генерировать JWT токен
        String token = jwtTokenProvider.generateToken(user);

        return new AuthResponse(token, toUserResponse(user));
    }

    /**
     * Получить информацию о текущем пользователе по токену
     */
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(String token) {
        // Получить email из токена
        String email = jwtTokenProvider.getUsernameFromToken(token);

        // Найти пользователя
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(
                        "Пользователь не найден"));

        return toUserResponse(user);
    }

    /**
     * Изменить пароль пользователя
     */
    @Transactional
    public void changePassword(String email, String oldPassword, String newPassword) {
        // Найти пользователя
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(
                        "Пользователь не найден"));

        // Проверить старый пароль
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadRequestException(
                    "Старый пароль неверный",
                    "INVALID_OLD_PASSWORD"
            );
        }

        // Валидировать новый пароль
        validatePassword(newPassword);

        // Обновить пароль
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * Получить пользователя по ID (для внутреннего использования)
     */
    @Transactional(readOnly = true)
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь", id));
    }

    /**
     * Получить всех модераторов
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getAllModerators() {
        return userRepository.findByRoleAndIsActiveTrue("MODERATOR")
                .stream()
                .map(this::toUserResponse)
                .toList();
    }

    /**
     * Валидировать пароль
     */
    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new BadRequestException(
                    "Пароль должен содержать минимум 8 символов",
                    "WEAK_PASSWORD"
            );
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new BadRequestException(
                    "Пароль должен содержать минимум одну заглавную букву",
                    "WEAK_PASSWORD"
            );
        }

        if (!password.matches(".*[0-9].*")) {
            throw new BadRequestException(
                    "Пароль должен содержать минимум одну цифру",
                    "WEAK_PASSWORD"
            );
        }
    }

    /**
     * Преобразовать UserEntity в UserResponse DTO
     */
    private UserResponse toUserResponse(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                user.getIsActive(),
                user.getCreatedAt(),
                user.getLastLoginAt()
        );
    }
}
