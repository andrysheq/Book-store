package com.example.library.usersservice.repository;

import com.example.library.usersservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository для работы с пользователями
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Найти пользователя по email
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Проверить существование пользователя по email
     */
    boolean existsByEmail(String email);

    /**
     * Найти всех активных пользователей с определённой ролью
     */
    java.util.List<UserEntity> findByRoleAndIsActiveTrue(String role);
}
