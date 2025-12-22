package com.example.library.mapper.directories;

import com.example.library.model.contract.directories.GenreView;
import com.example.library.model.dao.GenreEntity;
import org.springframework.stereotype.Component;

@Component
public class GenreConverter {

    public GenreView toGenreView(GenreEntity entity) {
        if (entity == null) {
            return null;
        }

        return new GenreView(
                entity.getId(),
                entity.getName()
        );
    }
}

