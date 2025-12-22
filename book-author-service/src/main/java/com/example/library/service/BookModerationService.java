package com.example.library.service;

import com.example.library.exception.BadRequestException;
import com.example.library.model.contract.book.BlockBookRequest;
import com.example.library.model.contract.book.BookModerationRegistryRequest;
import com.example.library.repository.BookRepository;
import com.example.library.service.spec.BookSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import com.example.library.model.dao.*;
import com.example.library.model.enums.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Сервис для операций модерации книг
 */
@Service
@RequiredArgsConstructor
public class BookModerationService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public Optional<BookEntity> getById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<BookEntity> getBookCatalog(BookModerationRegistryRequest request, Pageable pageable) {
        Specification<BookEntity> spec = buildSpecification(request);
        return bookRepository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    public Page<BookEntity> getBlockedBooks(Pageable pageable) {
        return bookRepository.findByBookStatus(BookStatusEnum.BLOCKED, pageable);
    }

    @Transactional
    public void blockBook(BookEntity book, BlockBookRequest request) {
        validateBlockingReason(request.reasonId());

        if (book.getBookStatus() == BookStatusEnum.BLOCKED) {
            throw new BadRequestException("Книга уже заблокирована ранее");
        }

        book.setBookStatus(BookStatusEnum.BLOCKED);
        book.setBlockingReason(BookBlockingReasonEnum.of(request.reasonId()));

        bookRepository.save(book);
    }

    @Transactional
    public void unblockBook(BookEntity book) {
        if (book.getBookStatus() == BookStatusEnum.AVAILABLE) {
            throw new BadRequestException("Книга уже разблокирована");
        }

        book.setBookStatus(BookStatusEnum.AVAILABLE);
        book.setBlockingReason(null);

        bookRepository.save(book);
    }

    private void validateBlockingReason(Integer reasonId) {
        if (BookBlockingReasonEnum.of(reasonId) == null) {
            throw new BadRequestException("Не существует причины отзыва с id = " + reasonId);
        }
    }

    private Specification<BookEntity> buildSpecification(BookModerationRegistryRequest request) {
        return new BookSpecificationBuilder()
                .withStatus(BookStatusEnum.of(request.statusId()))
                .withGenreId(request.genreId())
                .withSearchLike(request.searchLike())
                .build();
    }
}
