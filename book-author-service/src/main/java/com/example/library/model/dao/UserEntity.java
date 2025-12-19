package com.example.library.model.dao;

import com.example.library.config.AuditEntityListener;
import com.example.library.config.Auditable;
import com.example.library.model.enums.RoleEnum;
import com.example.library.model.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.io.Serializable;

/**
 * Сущность пользователя (читателя/модератора/администратора)
 */
@Entity
@Table(name = "\"user\"")
@EntityListeners(AuditEntityListener.class)
@Comment("Пользователи системы (читатели, модераторы, администраторы)")
@Getter
@Setter
public class UserEntity implements Auditable, Serializable {

    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Id
    @Column(name = "id")
    @Comment("Логический идентификатор")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    @Comment("Email пользователя (уникальный)")
    private String email;

    @Column(name = "first_name", nullable = false)
    @Comment("Имя пользователя")
    private String firstName;

    @Column(name = "password", nullable = false)
    @Comment("Хеш пароля (bcrypt)")
    private String password;

    @Column(name = "role_id", nullable = false)
    @Comment("Роль пользователя (1=ADMIN, 2=USER, 3=MODERATOR)")
    private RoleEnum role;

    @Column(name = "user_status_id", nullable = false)
    @Comment("Статус пользователя (1=Активен, 3=Заблокирован)")
    private UserStatusEnum userStatus;

    @Embedded
    private AuditEntity audit;
}