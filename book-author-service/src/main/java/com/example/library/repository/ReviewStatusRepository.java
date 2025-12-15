package com.example.library.repository;

import com.example.library.entity.ReviewStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewStatusRepository extends JpaRepository<ReviewStatusEntity, Long> {
    ReviewStatusEntity findByName(String name);
}
