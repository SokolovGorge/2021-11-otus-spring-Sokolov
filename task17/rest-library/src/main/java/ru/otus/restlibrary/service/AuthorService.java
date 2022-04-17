package ru.otus.restlibrary.service;

import ru.otus.restlibrary.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();
}
