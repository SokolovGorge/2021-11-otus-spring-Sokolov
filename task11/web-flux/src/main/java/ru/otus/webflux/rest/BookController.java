package ru.otus.webflux.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.webflux.domain.Book;
import ru.otus.webflux.dto.BookDto;
import ru.otus.webflux.repository.AuthorRepository;
import ru.otus.webflux.repository.BookRepository;
import ru.otus.webflux.repository.GenreRepository;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @GetMapping("/api/books")
    public Flux<BookDto> getAllBooks() {
        return bookRepository.findAll().map(BookDto::new);
    }

    @GetMapping("/api/books/{id}")
    public Mono<BookDto> getBook(@PathVariable("id") String id) {
        return bookRepository.findById(id).map(BookDto::new);
    }

    @PostMapping("/api/books")
    public Mono<BookDto> newBook(@RequestParam("title") String title,
                                 @RequestParam("authorId") String authorId,
                                 @RequestParam("genreId") String genreId) {
        return Mono.zip(authorRepository.findById(authorId), genreRepository.findById(genreId))
                .flatMap(tuple -> bookRepository.save(new Book(title, tuple.getT1(), tuple.getT2())))
                .map(BookDto::new);
    }

    @PutMapping("/api/books")
    public Mono<BookDto> saveBook(@RequestParam("id") String id,
                                  @RequestParam("title") String title,
                                  @RequestParam("authorId") String authorId,
                                  @RequestParam("genreId") String genreId) {
        return Mono.zip(authorRepository.findById(authorId), genreRepository.findById(genreId))
                .flatMap(tuple -> bookRepository.save(new Book(id, title, tuple.getT1(), tuple.getT2())))
                .map(BookDto::new);
    }

    @DeleteMapping("/api/books/{id}")
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
        return bookRepository.deleteById(id);
    }
}
