package com.example.library.service;

import com.example.library.model.dao.GenreEntity;
import com.example.library.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public List<GenreEntity> getAll(){
        return genreRepository.findAll();
    }
}
