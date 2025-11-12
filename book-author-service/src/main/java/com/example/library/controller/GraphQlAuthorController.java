package com.example.library.controller;

import com.example.library.dto.Author;
import com.example.library.dto.request.AuthorInput;
import com.example.library.dto.request.AuthorRecord;
import com.example.library.entity.AuthorEntity;
import com.example.library.mapper.BaseMapper;
import com.example.library.service.repo.AuthorRepoService;
import com.example.library.utils.StringUtils;
import com.example.library.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GraphQlAuthorController {

    private final AuthorRepoService authorRepoService;
    private final BaseMapper mapper;

    @MutationMapping(name = "addAuthor")
    public AuthorEntity addAuthor(@Argument AuthorInput input) {
        if (StringUtils.isBlank(input.firstName()) || StringUtils.isBlank(input.lastName()) || StringUtils.isBlank(input.birthDate())) {
            throw new IllegalArgumentException("firstName, lastName, birthDate must be non-empty");
        }

        LocalDate birthDate = TimeUtils.parseIsoDate(input.birthDate());

        AuthorRecord authorRecord = new AuthorRecord();
        authorRecord.setFirstName(input.firstName());
        authorRecord.setLastName(input.lastName());
        authorRecord.setMiddleName(input.middleName());
        authorRecord.setGender(input.gender());
        authorRecord.setBirthDate(birthDate);

        return authorRepoService.saveAuthor(authorRecord);
    }

    @QueryMapping(name = "authors")
    public List<Author> authors() {
        List<Author> authors = new ArrayList<>();
        authorRepoService.findAll()
                .forEach(author -> {
                    authors.add(mapper.map(author, Author.class));
                });
        return authors;
    }

    @QueryMapping(name = "authorById")
    public Author authorById(@Argument Long id) {
        AuthorEntity authorEntity = authorRepoService.findById(id);
        return authorEntity != null ? mapper.map(authorEntity, Author.class) : null;
    }
}
