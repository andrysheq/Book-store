package moderation.user.usermoderationservice.model.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import moderation.user.usermoderationservice.config.AuditEntityListener;
import moderation.user.usermoderationservice.config.Auditable;
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
