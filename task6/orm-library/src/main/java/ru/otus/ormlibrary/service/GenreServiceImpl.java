package ru.otus.ormlibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.ormlibrary.dto.GenreDto;
import ru.otus.ormlibrary.repositories.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream().map(GenreDto::new).collect(Collectors.toList());
    }
}
