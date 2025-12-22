package com.example.library.controller;

import com.example.library.handler.DirectoriesHandler;
import com.example.library.model.contract.directories.GenreView;
import com.example.library.security.RequireRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directories")
@Validated
@RequiredArgsConstructor
@Tag(name = "Directories", description = "Получение справочников")
public class DirectoryController {

    private final DirectoriesHandler directoriesHandler;

    @GetMapping("/genre")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение всех жанров")
    @RequireRole("MODERATOR")
    public List<GenreView> getGenres() {
        return directoriesHandler.getAllGenres();
    }
}
