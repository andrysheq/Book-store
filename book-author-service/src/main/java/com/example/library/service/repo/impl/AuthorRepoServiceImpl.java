package com.example.library.service.repo.impl;

import com.example.library.dto.Author;
import com.example.library.dto.request.AuthorRecord;
import com.example.library.exception.RecordNotFoundException;
import com.example.library.entity.AuthorEntity;
import com.example.library.repository.AuthorRepository;
import com.example.library.service.repo.AuthorRepoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthorRepoServiceImpl implements AuthorRepoService {
    private final AuthorRepository authorRepository;
    private final ModelMapper mapper;
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "authorById", key = "#id")
    public AuthorEntity findById(Long id) {
        Optional<AuthorEntity> authorOpt = authorRepository.findById(id);

        return authorOpt.orElseThrow(() -> new RecordNotFoundException("Автор не найден. id: " + id));
    }

    @Override
    @Transactional
    @CachePut(value = "authorById", key = "#result.id")
    public AuthorEntity saveAuthor(AuthorRecord author) {
        AuthorEntity authorEntity = mapper.map(author, AuthorEntity.class);
        return authorRepository.save(authorEntity);
    }

    @Override
    @Transactional
    @CachePut(value = "authorById", key = "#authorEntity.id")
    public AuthorEntity updateAuthor(AuthorEntity authorEntity) {
        return authorRepository.saveAndFlush(authorEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorEntity> findByIds(Iterable<Long> ids){
        return authorRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorEntity> findAll() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional
    @CacheEvict(value = "authorById", key = "#id")
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
}
