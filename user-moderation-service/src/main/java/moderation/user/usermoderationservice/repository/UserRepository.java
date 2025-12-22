package moderation.user.usermoderationservice.repository;

import moderation.user.usermoderationservice.model.dao.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    @NonNull
    Page<UserEntity> findAll(@NonNull Specification<UserEntity> spec, @NonNull Pageable pageable);
}
