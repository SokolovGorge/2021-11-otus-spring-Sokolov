package ru.otus.restlibrary.service;

import ru.otus.restlibrary.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> getAllGenres();
}
