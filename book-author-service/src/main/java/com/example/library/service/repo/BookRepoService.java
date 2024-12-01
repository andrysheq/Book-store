package com.example.library.service.repo;

import com.example.library.dto.request.BookRecord;
import com.example.library.entity.BookEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookRepoService {

    BookEntity findById(Long id);

    BookEntity saveBook(BookRecord book, Long userId);

    BookEntity updateBook(BookEntity bookEntity);

    List<BookEntity> findAll();

    void deleteById(Long id);
}
