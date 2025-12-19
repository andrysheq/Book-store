package com.example.library.usersservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сущность пользователя в системе
 */
@Entity
@Table(name = "\"user\"")
@Comment("Пользователи системы")
@Getter
@Setter
public class UserEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "id")
    @Comment("Логический идентификатор")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    @Comment("Email пользователя (уникальный)")
    private String email;

    @Column(name = "password", nullable = false)
    @Comment("Пароль (хеш)")
    private String password;

    @Column(name = "first_name", nullable = false)
    @Comment("Имя пользователя")
    private String firstName;

    @Column(name = "last_name")
    @Comment("Фамилия пользователя")
    private String lastName;

    @Column(name = "role", nullable = false)
    @Comment("Роль пользователя (USER, MODERATOR, ADMIN)")
    private String role;

    @Column(name = "is_active", nullable = false)
    @Comment("Активен ли пользователь")
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Comment("Дата и время создания")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Comment("Дата и время последнего обновления")
    private LocalDateTime updatedAt;

    @Column(name = "last_login_at")
    @Comment("Дата и время последнего входа")
    private LocalDateTime lastLoginAt;
}
