package ru.otus.jpalibrary.service;

import ru.otus.jpalibrary.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();
}
