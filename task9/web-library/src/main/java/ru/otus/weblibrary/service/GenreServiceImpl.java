package ru.otus.weblibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.weblibrary.dto.GenreDto;
import ru.otus.weblibrary.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements  GenreService {

    private final GenreRepository genreRepository;

    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream().map(GenreDto::new).collect(Collectors.toList());
    }
}
