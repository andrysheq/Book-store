package com.example.library.model.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "author")
@Comment("Авторы книг")
@Getter
@Setter
public class AuthorEntity implements Serializable {

    @SequenceGenerator(name = "author_seq", sequenceName = "author_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
    @Id
    @Column(name = "id")
    @Comment("Логический идентификатор")
    private Integer id;

    @Column(name = "last_name", nullable = false)
    @Comment("Фамилия автора")
    private String lastName;

    @Column(name = "first_name", nullable = false)
    @Comment("Имя автора")
    private String firstName;

    @Column(name = "email", nullable = false)
    @Comment("Email автора")
    private String email;

    @Column(name = "patronymic")
    @Comment("Отчество автора")
    private String patronymic;

    @Column(name = "birth_date")
    @Comment("Дата рождения автора")
    private LocalDate birthDate;
}
