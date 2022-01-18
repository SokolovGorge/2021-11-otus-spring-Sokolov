package ru.otus.jpalibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.jpalibrary.dto.AuthorDto;
import ru.otus.jpalibrary.repository.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream().map(AuthorDto::new).collect(Collectors.toList());
    }
}
