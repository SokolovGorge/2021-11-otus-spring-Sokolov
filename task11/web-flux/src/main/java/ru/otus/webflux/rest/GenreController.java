package ru.otus.webflux.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.webflux.dto.GenreDto;
import ru.otus.webflux.repository.GenreRepository;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreRepository repository;

    @GetMapping("/api/genres")
    public Flux<GenreDto> getAllGenres() {
        return repository.findAll().map(GenreDto::new);
    }
}
