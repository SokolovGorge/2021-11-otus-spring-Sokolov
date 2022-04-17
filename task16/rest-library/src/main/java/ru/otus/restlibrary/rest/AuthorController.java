package ru.otus.restlibrary.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.restlibrary.dto.AuthorDto;
import ru.otus.restlibrary.service.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/api/authors")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }
}
