package com.example.library.controller;

import com.example.library.dto.Author;
import com.example.library.dto.enums.Gender;
import com.example.library.dto.request.AuthorRecord;
import com.example.library.dto.request.Request;
import com.example.library.entity.AuthorEntity;
import com.example.library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthorRepository authorRepository;

    private final String baseUrl = "http://localhost:8081/authors";

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
    }

    @Test
    void testGetAllAuthors() {
        AuthorEntity author1 = new AuthorEntity("John", "Doe", "Middle", Gender.MALE, LocalDate.of(1980, 1, 1));
        AuthorEntity author2 = new AuthorEntity("Jane", "Smith", "Middle", Gender.MALE, LocalDate.of(1990, 2, 2));
        authorRepository.save(author1);
        authorRepository.save(author2);

        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("John", "Jane");
    }

    @Test
    void testGetAuthorById() {
        AuthorEntity author = new AuthorEntity("John", "Doe", "Middle", Gender.MALE, LocalDate.of(1980, 1, 1));
        authorRepository.save(author);

        ResponseEntity<Author> response = restTemplate.getForEntity(baseUrl + "/" + author.getId(), Author.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getFirstName()).isEqualTo("John");
    }

    @Test
    void testAddAuthor() {
        AuthorRecord authorRecord = new AuthorRecord("John", "Doe", null, Gender.MALE, LocalDate.of(1980, 1, 1));
        Request<AuthorRecord> request = new Request<>(authorRecord);

        ResponseEntity<Author> response = restTemplate.postForEntity(baseUrl, request, Author.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getFirstName()).isEqualTo("John");
    }

    @Test
    void testUpdateAuthor() {
        AuthorEntity author = new AuthorEntity("John", "Doe", "Middle", Gender.MALE, LocalDate.of(1980, 1, 1));
        authorRepository.save(author);
        AuthorRecord updatedRecord = new AuthorRecord("UpdatedJohn", "Doe", null, Gender.MALE, LocalDate.of(1980, 1, 1));
        Request<AuthorRecord> request = new Request<>(updatedRecord);

        ResponseEntity<Author> response = restTemplate.exchange(baseUrl + "/" + author.getId(), HttpMethod.PUT, new HttpEntity<>(request), Author.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getFirstName()).isEqualTo("UpdatedJohn");
    }

    @Test
    void testDeleteAuthor() {
        AuthorEntity author = new AuthorEntity("John", "Doe", "Middle", Gender.MALE, LocalDate.of(1980, 1, 1));
        authorRepository.save(author);

        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/" + author.getId(), HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(authorRepository.findById(author.getId())).isEmpty();
    }
}
