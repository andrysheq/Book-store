package com.example.library.repository;

import com.example.library.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query("""
        SELECT b FROM BookEntity b 
        LEFT JOIN FETCH b.author 
        LEFT JOIN FETCH b.genre 
        WHERE b.id = :id
    """)
    BookEntity findByIdWithDetails(@Param("id") Long id);

    @Query("""
        SELECT b FROM BookEntity b 
        WHERE b.title ILIKE %:title% 
        ORDER BY b.title
    """)
    Page<BookEntity> findByTitleContaining(@Param("title") String title, Pageable pageable);

    @Query("""
        SELECT b FROM BookEntity b 
        WHERE b.author.id = :authorId
    """)
    Page<BookEntity> findByAuthorId(@Param("authorId") Long authorId, Pageable pageable);
}
