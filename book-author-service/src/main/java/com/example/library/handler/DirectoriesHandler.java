package com.example.library.handler;

import com.example.library.mapper.directories.GenreConverter;
import com.example.library.model.contract.directories.GenreView;
import com.example.library.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DirectoriesHandler {

    private final GenreService genreService;
    private final GenreConverter genreConverter;

    @Transactional(readOnly = true)
    public List<GenreView> getAllGenres(){
        return genreService.getAll().stream().map(genreConverter::toGenreView).toList();
    }
}
