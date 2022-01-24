package ru.otus.mongolibrary.service;

import ru.otus.mongolibrary.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();
}
