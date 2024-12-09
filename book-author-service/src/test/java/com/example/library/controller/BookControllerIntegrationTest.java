package com.example.library.controller;

import com.example.library.dto.Author;
import com.example.library.dto.Book;
import com.example.library.dto.enums.Gender;
import com.example.library.dto.enums.StatusType;
import com.example.library.dto.request.BookRecord;
import com.example.library.dto.request.Request;
import com.example.library.entity.AuthorEntity;
import com.example.library.entity.BookEntity;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private final String baseUrl = "http://localhost:8081/books";
    private final String userIdURl = "?userId=1";

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
    }

    @Test
    void testGetAllBooks() {
        BookEntity book1 = new BookEntity("Book One", 200, 1L, StatusType.CONFIRMED);
        BookEntity book2 = new BookEntity("Book Two", 300, 1L, StatusType.CONFIRMED);
        bookRepository.save(book1);
        bookRepository.save(book2);

        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("Book One", "Book Two");
    }

    @Test
    void testGetBookById() {
        BookEntity book = new BookEntity("Book One", 200, 1L, StatusType.CONFIRMED);
        book = bookRepository.save(book);

        ResponseEntity<Book> response = restTemplate.getForEntity(baseUrl + "/" + book.getId(), Book.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getTitle()).isEqualTo("Book One");
    }

    @Test
    void testAddBook() {
        BookRecord bookRecord = new BookRecord("Book One", 200, Collections.emptySet());
        Request<BookRecord> request = new Request<>(bookRecord);

        ResponseEntity<Book> response = restTemplate.postForEntity(baseUrl+userIdURl, request, Book.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getTitle()).isEqualTo("Book One");
    }

    @Test
    void testDeleteBook() {
        AuthorEntity author = new AuthorEntity("John", "Doe", "Middle", Gender.MALE, LocalDate.of(1980, 1, 1));
        authorRepository.save(author);

        BookRecord bookRecord = new BookRecord("Book One", 200, Set.of(author.getId()));
        Request<BookRecord> request = new Request<>(bookRecord);

        ResponseEntity<Book> response = restTemplate.postForEntity(baseUrl+userIdURl, request, Book.class);

        ResponseEntity<Void> responseDelete = restTemplate.exchange(baseUrl + "/" + response.getBody().getId(), HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
}
