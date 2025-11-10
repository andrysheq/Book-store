package com.example.library.service;

import com.example.library.aop.TrackBookCreation;
import com.example.library.dto.Book;
import com.example.library.dto.request.BookRecord;
import com.example.library.dto.request.Request;
import com.example.library.dto.response.FindBooksResponse;
import com.example.library.mapper.BaseMapper;
import com.example.library.entity.BookEntity;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import com.example.library.service.repo.BookRepoService;
import com.sun.jdi.InternalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookHandler {
    private final BaseMapper mapper;
    private final BookRepoService bookRepoService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @TrackBookCreation
    public Book addBook(Request<BookRecord> request) {
        BookRecord book = request.getPayload();
        BookEntity savedBook = bookRepoService.saveBook(book);

        Set<Long> authorIds = book.getAuthorIds();
        if (authorIds != null && !authorIds.isEmpty()) {
            for (Long authorId : authorIds) {
                authorRepository.findById(authorId).orElseThrow(() -> new InternalException("Не найден автор с id: "+ authorId));
                bookRepository.saveBookAuthor(savedBook.getId(), authorId);
            }
        }

        return mapper.map(savedBook, Book.class);
    }

    public Book getBook(Long id) {
        BookEntity bookEntity = bookRepoService.findById(id);;

        return mapper.map(bookEntity, Book.class);
    }

    public FindBooksResponse findAllBooks() {
        List<BookEntity> bookList = bookRepoService.findAll();

        return FindBooksResponse.builder()
                .data(mapper.convertList(bookList, Book.class))
                .build();
    }

    public Book updateBook(Long id, Request<BookRecord> request) {
        BookRecord book = request.getPayload();
        BookEntity bookEntity = bookRepoService.findById(id);
        bookEntity.setTitle(book.getTitle());
        bookEntity.setPageAmount(book.getPageAmount());

        bookRepository.deleteBookAuthorsByBookId(id);

        Set<Long> authorIds = request.getPayload().getAuthorIds();
        if (authorIds != null && !authorIds.isEmpty()) {
            for (Long authorId : authorIds) {
                bookRepository.saveBookAuthor(bookEntity.getId(), authorId);
            }
        }

        return mapper.map(bookRepoService.updateBook(bookEntity), Book.class);
    }

    public void deleteBook(Long id) {
        bookRepoService.deleteById(id);
    }
}
