package moderation.user.usermoderationservice.config.jpa;

import com.example.library.config.jpa.JpaSearchRepositoryImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableJpaRepositories(repositoryBaseClass = JpaSearchRepositoryImpl.class)
public @interface EnableJpaSearchRepository {
    String[] basePackages() default {};
}
