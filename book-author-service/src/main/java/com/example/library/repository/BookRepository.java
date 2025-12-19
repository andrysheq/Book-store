package com.example.library.repository;

import com.example.library.model.dao.BookEntity;
import com.example.library.model.enums.BookStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @NonNull
    Page<BookEntity> findAll(@NonNull Specification<BookEntity> spec, @NonNull Pageable pageable);

    Page<BookEntity> findByBookStatus(BookStatusEnum status, Pageable pageable);

}
