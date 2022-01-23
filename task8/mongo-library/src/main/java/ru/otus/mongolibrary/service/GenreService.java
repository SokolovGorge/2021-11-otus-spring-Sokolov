package ru.otus.mongolibrary.service;

import ru.otus.mongolibrary.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> getAllGenres();
}
