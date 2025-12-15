package com.example.library.repository;

import com.example.library.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    @Query("""
        SELECT u FROM UserEntity u 
        WHERE u.role.name = :roleName
    """)
    Page<UserEntity> findByRole(@Param("roleName") String roleName, Pageable pageable);

    @Query("""
        SELECT u FROM UserEntity u 
        WHERE u.userStatus.name = :statusName
    """)
    Page<UserEntity> findByStatus(@Param("statusName") String statusName, Pageable pageable);

    @Query("""
        SELECT u FROM UserEntity u 
        WHERE u.createdDate BETWEEN :startDate AND :endDate 
        ORDER BY u.createdDate DESC
    """)
    Page<UserEntity> findByCreatedDateRange(@Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate,
                                      Pageable pageable);

    // Найти активных модераторов
    @Query("""
        SELECT u FROM UserEntity u 
        WHERE u.role.name = 'Moderator' 
        AND u.userStatus.name = 'Active'
    """)
    Page<UserEntity> findActiveModerators(Pageable pageable);
}