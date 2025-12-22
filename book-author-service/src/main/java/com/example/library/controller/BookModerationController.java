package com.example.library.controller;

import com.example.library.handler.BookModerationHandler;
import com.example.library.model.contract.book.BlockBookRequest;
import com.example.library.model.contract.book.BookDetailView;
import com.example.library.model.contract.book.BookModerationRegistryRequest;
import com.example.library.model.contract.book.BookRegistryView;
import com.example.library.security.RequireRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@Validated
@RequiredArgsConstructor
@Tag(name = "BookModeration", description = "Модерация книг")
public class BookModerationController {

    private final BookModerationHandler bookModerationHandler;

    @PostMapping("/catalog")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение каталога книг для модератора")
    @RequireRole("MODERATOR")
    public Page<BookRegistryView> getBookCatalog(
            @Valid @RequestBody BookModerationRegistryRequest request,
            @ParameterObject
            Pageable pageable) {
        return bookModerationHandler.getBookCatalog(request, pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение полной информации о книге с рецензиями")
    @RequireRole("MODERATOR")
    public BookDetailView getBookDetail(
            @Parameter(description = "Идентификатор книги")
            @NotNull
            @PathVariable("id")
            Long bookId) {
        return bookModerationHandler.getBookDetail(bookId);
    }

    @PutMapping("/{id}/block")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Блокировка книги с указанием причины")
    @RequireRole("MODERATOR")
    public void blockBook(
            @Parameter(description = "Идентификатор книги")
            @NotNull
            @PathVariable("id")
            Long bookId,
            @Valid @RequestBody @NotNull
            @Parameter(description = "Запрос на блокировку книги")
            BlockBookRequest request) {
        bookModerationHandler.blockBook(bookId, request);
    }

    @PutMapping("/{id}/unblock")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Разблокировка книги")
    @RequireRole("MODERATOR")
    public void unblockBook(
            @Parameter(description = "Идентификатор книги")
            @NotNull
            @PathVariable("id")
            Long bookId) {
        bookModerationHandler.unblockBook(bookId);
    }
}
