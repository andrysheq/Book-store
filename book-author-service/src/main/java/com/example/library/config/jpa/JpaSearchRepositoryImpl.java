package com.example.library.config.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.Optional;

public class JpaSearchRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements JpaSearchRepository<T, ID> {
    public JpaSearchRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public Optional<T> findFirst(Specification<T> spec) {
        try {
            return Optional.of(getQuery(spec, Sort.unsorted()).setMaxResults(1).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
