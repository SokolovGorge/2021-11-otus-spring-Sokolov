package ru.otus.ormlibrary.service;

import ru.otus.ormlibrary.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();
}
