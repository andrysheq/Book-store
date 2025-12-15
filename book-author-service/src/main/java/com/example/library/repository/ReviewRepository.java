package com.example.library.repository;

import com.example.library.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    // Получить все отзывы по книге
    @Query("""
        SELECT r FROM ReviewEntity r
        WHERE r.book.id = :bookId
        ORDER BY r.createdDate DESC
    """)
    Page<ReviewEntity> findByBookId(@Param("bookId") Long bookId, Pageable pageable);

    /**
     * Получить отчет по ценовым диапазонам книг с рейтингом
     */
    @Query(value = """
        SELECT
            (FLOOR(b.price / 100)::INTEGER * 100)::VARCHAR || 
            '-' || 
            ((FLOOR(b.price / 100)::INTEGER + 1) * 100)::VARCHAR AS price_range,
            COUNT(DISTINCT b.id) AS book_count,
            ROUND(AVG(r.rating::NUMERIC), 2) AS average_rating,
            COUNT(r.id) AS review_count
        FROM book b
        LEFT JOIN review r ON b.id = r.book_id 
            AND r.review_status_id = (
                SELECT id FROM review_status WHERE id = 2
            )
        GROUP BY FLOOR(b.price / 100)
        ORDER BY FLOOR(b.price / 100) ASC
        """, nativeQuery = true)
    List<Object[]> findBookPriceBucketReport();
}
