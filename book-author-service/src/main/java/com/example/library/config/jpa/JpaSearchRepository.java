package com.example.library.config.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaSearchRepository<T, ID> extends JpaRepository<T, ID>, ExtendedJpaSearchSpecificationExecutor<T> {
}
