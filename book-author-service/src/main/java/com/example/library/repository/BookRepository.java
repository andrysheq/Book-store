package com.example.library.repository;

import com.example.library.entity.AuthorEntity;
import com.example.library.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends BaseRepository<BookEntity> {

    @Transactional
    BookEntity saveAndFlush(BookEntity bookEntity);

    @Override
    @Transactional(readOnly = true)
    @Query("select b from BookEntity b")
    List<BookEntity> findAll();

    @Override
    @Transactional(readOnly = true)
    @Query("select b from BookEntity b")
    Page<BookEntity> findAll(Pageable pageable);

    @Override
    @Transactional(readOnly = true)
    @Query("select b from BookEntity b where b.id = ?1")
    Optional<BookEntity> findById(Long id);

    @Transactional
    @Modifying
    @Query(value = "insert into book_author (book_id, author_id) values (:bookId, :authorId)", nativeQuery = true)
    void saveBookAuthor(@Param("bookId") Long bookId, @Param("authorId") Long authorId);

    @Transactional
    @Query(value = "DELETE FROM book_author WHERE book_id = :bookId", nativeQuery = true)
    void deleteBookAuthorsByBookId(@Param("bookId") Long bookId);


}
