package com.example.library.model.dao;

import com.example.library.config.AuditEntityListener;
import com.example.library.config.Auditable;
import com.example.library.model.enums.ReviewRejectionReasonEnum;
import com.example.library.model.enums.ReviewStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Сущность рецензии на книгу (требует модерации)
 */
@Entity
@Table(name = "review")
@EntityListeners(AuditEntityListener.class)
@Comment("Рецензии на книги (требуют модерации)")
@Getter
@Setter
public class ReviewEntity implements Auditable, Serializable {

    @SequenceGenerator(name = "review_seq", sequenceName = "review_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_seq")
    @Id
    @Column(name = "id")
    @Comment("Логический идентификатор")
    private Long id;

    @Column(name = "content", length = 512)
    @Comment("Текст рецензии")
    private String content;

    @Column(name = "rating", nullable = false)
    @Comment("Рейтинг (от 1 до 5)")
    private Integer rating;

    @Column(name = "status_updated_at")
    @Comment("Дата последнего изменения статуса модератором")
    private LocalDateTime statusUpdatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    @Comment("Ссылка на книгу")
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("Ссылка на пользователя-автора рецензии")
    private UserEntity user;

    @Column(name = "review_status_id", nullable = false)
    @Comment("Статус рецензии (1=На рассмотрении, 2=Подтвержден, 3=Отклонен)")
    private ReviewStatusEnum reviewStatus;

    @Column(name = "rejection_reason_id")
    @Comment("Причина отклонения рецензии (заполняется если статус=Отклонен)")
    private ReviewRejectionReasonEnum rejectionReason;

    @Column(name = "rejection_comment", length = 512)
    @Comment("Комментарий модератора к отклонению")
    private String rejectionComment;

    @Embedded
    private AuditEntity audit;

    @PrePersist
    public void preUpdate() {
        setStatusUpdatedAt(LocalDateTime.now());
    }
}