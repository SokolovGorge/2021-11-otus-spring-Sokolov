package ru.otus.weblibrary.service;

import ru.otus.weblibrary.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();
}
