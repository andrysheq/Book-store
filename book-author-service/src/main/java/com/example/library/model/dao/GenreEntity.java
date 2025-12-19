package com.example.library.model.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.io.Serializable;

/**
 * Сущность жанра книги
 */
@Entity
@Table(name = "genre")
@Comment("Жанры книг")
@Getter
@Setter
public class GenreEntity implements Serializable {

    @Id
    @Column(name = "id")
    @Comment("Логический идентификатор")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    @Comment("Наименование жанра")
    private String name;
}
