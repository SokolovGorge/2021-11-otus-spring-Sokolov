package ru.otus.restlibrary.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.restlibrary.dto.GenreDto;
import ru.otus.restlibrary.repository.GenreRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @HystrixCommand(commandKey = "getGenreKey", fallbackMethod = "getDummyList")
    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream().map(GenreDto::new).collect(Collectors.toList());
    }

    public List<GenreDto> getDummyList() {
        return Collections.singletonList(GenreDto.DUMMY);
    }
}
