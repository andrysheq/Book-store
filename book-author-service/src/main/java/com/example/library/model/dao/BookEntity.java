package com.example.library.model.dao;

import com.example.library.model.enums.BookBlockingReasonEnum;
import com.example.library.model.enums.BookStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.util.List;

/**
 * Сущность книги в каталоге
 */
@Entity
@Table(name = "book")
@Comment("Электронные книги в каталоге магазина")
@Getter
@Setter
public class BookEntity implements Serializable {

    @SequenceGenerator(name = "book_seq", sequenceName = "book_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @Id
    @Column(name = "id")
    @Comment("Логический идентификатор")
    private Long id;

    @Column(name = "title", nullable = false)
    @Comment("Название книги")
    private String title;

    @Column(name = "price", nullable = false)
    @Comment("Цена книги в копейках (целое число)")
    private Integer price;

    @Column(name = "book_status_id", nullable = false)
    @Comment("Статус книги (1=В наличии, 2=Заблокирована)")
    private BookStatusEnum bookStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    @Comment("Ссылка на автора")
    private AuthorEntity author;

    @Column(name = "description", nullable = false, length = 512)
    @Comment("Описание книги")
    private String description;

    @Column(name = "book_blocking_reason_id")
    @Comment("Причина блокировки книги (заполняется если статус=Заблокирована)")
    private BookBlockingReasonEnum blockingReason;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "genre_id", nullable = false)
    @Comment("Ссылка на жанр")
    private GenreEntity genre;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("Список рецензий на эту книгу")
    private List<ReviewEntity> reviews;
}
