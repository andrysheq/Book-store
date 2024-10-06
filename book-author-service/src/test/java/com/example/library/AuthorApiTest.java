package com.example.library;

import com.example.library.dto.Author;
import com.example.library.dto.request.Request;
import com.example.library.entity.AuthorEntity;
import com.example.library.dto.enums.Gender;
import com.example.library.repository.AuthorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        // Очистить базу данных перед каждым тестом
        authorRepository.deleteAll();
    }

    /**
     * Тест добавления 100 элементов
     */
    @Test
    void shouldAdd100Authors() throws Exception {
        IntStream.range(0, 100).forEach(i -> {
            try {
                shouldAddAuthor();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Проверка, что 100 авторов добавлены
        assertThat(authorRepository.count()).isEqualTo(100);
    }

    /**
     * Тест добавления 100 000 элементов
     */
    @Test
    void shouldAdd100000Authors() throws Exception {
        IntStream.range(0, 100_000).forEach(i -> {
            try {
                shouldAddAuthor();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Проверка, что 100 000 авторов добавлены
        assertThat(authorRepository.count()).isEqualTo(100_000);
    }

    @Test
    public void shouldAddAuthor() throws Exception {
        Author author = Author.builder()
                .firstName("FirstName0")
                .lastName("LastName0")
                .middleName(null)
                .gender(Gender.MALE)
                .birthDate(LocalDate.parse("1980-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
        Request<Author> request = new Request<Author>(author);

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }



    /**
     * Тест на удаление всех элементов
     */
    @Test
    void shouldDeleteAllAuthors() throws Exception {
        authorRepository.deleteAll();

        // Проверяем, что все записи удалены
        assertThat(authorRepository.count()).isEqualTo(0);
    }
    
}

