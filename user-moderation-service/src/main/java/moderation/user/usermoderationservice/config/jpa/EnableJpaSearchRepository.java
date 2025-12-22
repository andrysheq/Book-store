package moderation.user.usermoderationservice.config.jpa;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableJpaRepositories(repositoryBaseClass = JpaSearchRepositoryImpl.class)
public @interface EnableJpaSearchRepository {
    String[] basePackages() default {};
}
