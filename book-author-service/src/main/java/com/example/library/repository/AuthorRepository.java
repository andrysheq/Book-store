package com.example.library.repository;

import com.example.library.entity.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface AuthorRepository extends BaseRepository<AuthorEntity> {
    @Transactional
    AuthorEntity saveAndFlush(AuthorEntity authorEntity);
    @Transactional(readOnly = true)
    @Query(value = "SELECT a.* FROM author a INNER JOIN book_author ba ON a.id = ba.author_id WHERE ba.book_id = :bookId", nativeQuery = true)
    List<AuthorEntity> findAuthorsByBookId(@Param("bookId") Long bookId);


    @Transactional(readOnly = true)
    @Query(value = "SELECT author_id FROM book_author WHERE book_id = :bookId", nativeQuery = true)
    Set<Long> findAuthorIdsByBookId(@Param("bookId") Long bookId);
}
