package com.example.library.config.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface JpaSearchSpecificationExecutor<T> extends JpaSpecificationExecutor<T> {
    default Page<T> findAll(String search, Pageable pageable) {
        Specification<T> spec = SearchHelper.createJPASpecification(search);
        return findAll(spec, pageable);
    }

    default List<T> findAll(String search, Sort sort) {
        Specification<T> spec = SearchHelper.createJPASpecification(search);
        return findAll(spec, sort);
    }

    default List<T> findAll(String search) {
        Specification<T> spec = SearchHelper.createJPASpecification(search);
        return findAll(spec);
    }

    default Optional<T> findOne(String search) {
        Specification<T> spec = SearchHelper.createJPASpecification(search);
        return findOne(spec);
    }

    default Page<T> findAll(Specification<T> spec, String search, Pageable pageable) {
        return findAll(SearchHelper.createJPASpecification(spec, search), pageable);
    }

    default List<T> findAll(Specification<T> spec, String search, Sort sort) {
        return findAll(SearchHelper.createJPASpecification(spec, search), sort);
    }

    default List<T> findAll(Specification<T> spec, String search) {
        return findAll(SearchHelper.createJPASpecification(spec, search));
    }

    default Optional<T> findOne(Specification<T> spec, String search) {
        return findOne(SearchHelper.createJPASpecification(spec, search));
    }
}
