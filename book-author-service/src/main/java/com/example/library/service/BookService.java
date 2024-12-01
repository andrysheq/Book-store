package com.example.library.service;

import com.example.library.dto.Author;
import com.example.library.dto.Book;
import com.example.library.dto.enums.StatusType;
import com.example.library.dto.request.BookRecord;
import com.example.library.dto.request.Request;
import com.example.library.dto.response.BookResponse;
import com.example.library.dto.response.FindBooksResponse;
import com.example.library.kafka.ObjectProducer;
import com.example.library.mapper.BaseMapper;
import com.example.library.entity.BookEntity;
import com.example.library.repository.BookRepository;
import com.example.library.service.repo.BookRepoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookService {
    private final BaseMapper mapper;
    private final BookRepoService bookRepoService;
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final ObjectProducer objectProducer;

    public Book addBook(Request<BookRecord> request, Long userId) {
        BookRecord book = request.getPayload();
        BookEntity savedBook = bookRepoService.saveBook(book, userId);

        Set<Long> authorIds = book.getAuthorIds();
        if (authorIds != null && !authorIds.isEmpty()) {
            for (Long authorId : authorIds) {
                bookRepository.saveBookAuthor(savedBook.getId(), authorId);
            }
        }

        objectProducer.sendAddRequest(savedBook.getId(), userId);

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

    public void confirmObjectAddition(Long objectId) {
        BookEntity book = bookRepoService.findById(objectId);
        book.setStatus(StatusType.CONFIRMED);
        bookRepository.saveAndFlush(book);
    }
}
