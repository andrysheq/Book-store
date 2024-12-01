package com.example.library.controller;

import com.example.library.dto.Book;
import com.example.library.dto.error.ErrorResponse;
import com.example.library.dto.request.BookRecord;
import com.example.library.dto.request.Request;
import com.example.library.dto.response.BookResponse;
import com.example.library.dto.response.FindBooksResponse;
import com.example.library.service.BookService;
import com.example.library.utils.RestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping()
@Tag(
    name = "Контроллер для работы с книгами",
    description = "Все методы для работы с книгами"
)
public class BookController {
    private static final String BOOKS_URL = "/books";
    private static final String BOOKS_ID_URL = "/books/{id}";
    private final BookService bookService;

    @Operation(
            summary = "Получить список книг"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FindBooksResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @GetMapping(
            path = BOOKS_URL,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FindBooksResponse> getAllBooks(){//@RequestHeader("Authorization") String authorization) {

        return RestUtils.responseOf(bookService::findAllBooks);
    }

    @Operation(
            summary = "Получить книгу по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Book.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @GetMapping(
            path = BOOKS_ID_URL,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Book> getBook(
            @PathVariable(name = "id") Long bookId) {

        return RestUtils.responseOf(() -> bookService.getBook(bookId));
    }

    @Operation(
            summary = "Добавить книгу"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Book.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @PostMapping(
            path = BOOKS_URL,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Book> addBook(
            @Parameter(name = "Book", required = true) @Valid @RequestBody Request<BookRecord> request,
            @RequestParam("userId") Long userId) {

        return RestUtils.responseOf(request, req -> bookService.addBook(request,userId));
    }

    @Operation(
            summary = "Удалить книгу по id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(schema = @Schema())),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @DeleteMapping(
            path = BOOKS_ID_URL,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID книги")
            @PathVariable Long id) {

        bookService.deleteBook(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Обновить информацию о книге"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Book.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @PutMapping(
            path = BOOKS_ID_URL,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Book> updateBook(
            @PathVariable(name = "id") Long bookId,
            @Parameter(name = "Book", required = true) @Valid @RequestBody Request<BookRecord> request) {

        return RestUtils.responseOf(request, req -> bookService.updateBook(bookId, req));
    }
}
