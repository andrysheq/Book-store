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

    @Column(name = "user_status_id", nullable = false)
    @Comment("Статус пользователя (1=Активен, 3=Заблокирован)")
    private UserStatusEnum userStatus;

    @Embedded
    private AuditEntity audit;
}