package com.example.library.config;

import com.example.library.dto.Author;
import com.example.library.dto.Book;
import com.example.library.dto.response.BookResponse;
import com.example.library.entity.AuthorEntity;
import com.example.library.entity.BookEntity;
import com.example.library.mapper.BaseMapper;
import com.example.library.repository.AuthorRepository;
import com.example.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.modelmapper.config.Configuration.AccessLevel.PUBLIC;

@RequiredArgsConstructor
@Configuration
public class MapperConfiguration {
    private final AuthorRepository authorRepository;
    @Bean
    public BaseMapper modelMapper() {
        BaseMapper mapper = new BaseMapper();

        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setAmbiguityIgnored(true)
                .setFieldAccessLevel(PUBLIC);

        createTypeMaps(mapper);

        return mapper;
    }

    private void createTypeMaps(BaseMapper mapper) {
        mapper.createTypeMap(BookEntity.class, BookResponse.class)
                .addMappings(expr -> {
                    expr.skip(BookResponse::setAuthors);
                })
                .setPostConverter(
                        ctx -> {
                            final BookEntity source = ctx.getSource();
                            final BookResponse destination = ctx.getDestination();

                            destination.setAuthors(mapper.convertSet(
                                    authorRepository.findAuthorsByBookId(source.getId()), Author.class));

                            return ctx.getDestination();
                        });

        mapper.createTypeMap(BookEntity.class, Book.class)
                .addMappings(expr -> {
                    expr.skip(Book::setAuthorIds);
                })
                .setPostConverter(
                        ctx -> {
                            final BookEntity source = ctx.getSource();
                            final Book destination = ctx.getDestination();

                            destination.setAuthorIds(authorRepository.findAuthorIdsByBookId(source.getId()));
                            return ctx.getDestination();
                        });
    }
}
