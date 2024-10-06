package com.example.library.service;

import com.example.library.dto.Author;
import com.example.library.dto.request.AuthorRecord;
import com.example.library.dto.request.Request;
import com.example.library.dto.response.FindAuthorsResponse;
import com.example.library.mapper.BaseMapper;
import com.example.library.entity.AuthorEntity;
import com.example.library.service.repo.AuthorRepoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthorService {
    private final BaseMapper mapper;
    private final AuthorRepoService authorRepoService;

    public Author addAuthor(Request<AuthorRecord> request) {
        AuthorRecord author = request.getPayload();

        return mapper.map(authorRepoService.saveAuthor(author), Author.class);
    }

    public Author getAuthor(Long id) {
        AuthorEntity authorEntity = authorRepoService.findById(id);

        return mapper.map(authorEntity, Author.class);
    }

    public FindAuthorsResponse findAllAuthors() {
        List<AuthorEntity> authorList = authorRepoService.findAll();

        return FindAuthorsResponse.builder()
                .data(mapper.convertList(authorList, Author.class))
                .build();
    }

    public List<AuthorEntity> findAuthorsByIds(Iterable<Long> ids) {
        return authorRepoService.findByIds(ids);
    }

    public Author updateAuthor(Long id, Request<AuthorRecord> request) {
        AuthorRecord author = request.getPayload();
        AuthorEntity authorEntity = authorRepoService.findById(id);
        authorEntity.setBirthDate(author.getBirthDate());
        authorEntity.setGender(author.getGender());
        authorEntity.setFirstName(author.getFirstName());
        authorEntity.setLastName(author.getLastName());
        authorEntity.setMiddleName(author.getMiddleName());

        return mapper.map(authorRepoService.updateAuthor(authorEntity), Author.class);
    }

    public void deleteAuthor(Long id) {
        authorRepoService.deleteById(id);
    }
}
