package ru.otus.webflux.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.webflux.dto.AuthorDto;
import ru.otus.webflux.repository.AuthorRepository;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository repository;

    @GetMapping("/api/authors")
    public Flux<AuthorDto> getAllAuthors() {
        return repository.findAll().map(AuthorDto::new);
    }
}
