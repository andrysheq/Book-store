package com.example.library.handler;

import com.example.library.exception.NotFoundException;
import com.example.library.mapper.book.BookModerationConverter;
import com.example.library.model.contract.book.BookDetailView;
import com.example.library.model.contract.book.BookModerationRegistryRequest;
import com.example.library.model.contract.book.BookRegistryView;
import com.example.library.model.contract.book.BlockBookRequest;
import com.example.library.model.dao.BookEntity;
import com.example.library.service.BookModerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class BookModerationHandler {

    private final BookModerationService bookModerationService;
    private final BookModerationConverter bookModerationConverter;

    @Transactional(readOnly = true)
    public Page<BookRegistryView> getBookCatalog(BookModerationRegistryRequest request, Pageable pageable) {
        return bookModerationService.getBookCatalog(request, pageable)
                .map(bookModerationConverter::toBookRegistryView);
    }

    @Transactional(readOnly = true)
    public BookDetailView getBookDetail(Long bookId) {
        BookEntity book = bookModerationService.getById(bookId)
                .orElseThrow(() -> new NotFoundException("Книга с id = " + bookId + " не найдена"));
        return bookModerationConverter.toBookDetailView(book);
    }

    @Transactional(readOnly = true)
    public Page<BookRegistryView> getBlockedBooks(Pageable pageable) {
        return bookModerationService.getBlockedBooks(pageable)
                .map(bookModerationConverter::toBookRegistryView);
    }

    @Transactional
    public void blockBook(Long bookId, BlockBookRequest request) {
        BookEntity book = bookModerationService.getById(bookId)
                .orElseThrow(() -> new NotFoundException("Книга с id = " + bookId + " не найдена"));

        bookModerationService.blockBook(book, request);
    }

    @Transactional
    public void unblockBook(Long bookId) {
        BookEntity book = bookModerationService.getById(bookId)
                .orElseThrow(() -> new NotFoundException("Книга с id = " + bookId + " не найдена"));

        bookModerationService.unblockBook(book);
    }
}
