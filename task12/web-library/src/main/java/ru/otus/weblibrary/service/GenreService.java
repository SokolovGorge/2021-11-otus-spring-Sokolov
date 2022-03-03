package ru.otus.weblibrary.service;

import ru.otus.weblibrary.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> getAllGenres();
}
