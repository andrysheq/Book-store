package com.example.library.repository;

import com.example.library.model.dao.BookEntity;
import com.example.library.model.dao.ReviewEntity;
import com.example.library.model.enums.ReviewStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    Page<ReviewEntity> findByReviewStatus(ReviewStatusEnum status, Pageable pageable);

    @NonNull
    Page<ReviewEntity> findAll(@NonNull Specification<ReviewEntity> spec, @NonNull Pageable pageable);
}
