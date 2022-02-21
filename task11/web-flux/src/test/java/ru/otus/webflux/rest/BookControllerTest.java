package ru.otus.webflux.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.webflux.domain.Author;
import ru.otus.webflux.domain.Book;
import ru.otus.webflux.domain.Genre;
import ru.otus.webflux.repository.AuthorRepository;
import ru.otus.webflux.repository.BookRepository;
import ru.otus.webflux.repository.GenreRepository;

import java.util.HashMap;
import java.util.List;


@DisplayName("Контроллер книг должен")
@WebFluxTest(controllers = BookController.class)
class BookControllerTest {

    private static final String EXISTING_BOOK_ID1 = "1";
    private static final String EXISTING_BOOK_TITLE1 = "Восточный экспресс";
    private static final String EXISTING_BOOK_ID2 = "2";
    private static final String EXISTING_BOOK_TITLE2 = "Императоры иллюзий";
    private static final String EXISTING_GENRE_ID1 = "1";
    private static final String EXISTING_GENRE_NAME1 = "Детектив";
    private static final String EXISTING_AUTHOR_ID1 = "1";
    private static final String EXISTING_AUTHOR_FIRSTNAME1 = "Агата";
    private static final String EXISTING_AUTHOR_LASTNAME1 = "Кристи";
    private static final String EXISTING_AUTHOR_ID2 = "2";
    private static final String EXISTING_AUTHOR_FIRSTNAME2 = "Сергей";
    private static final String EXISTING_AUTHOR_LASTNAME2 = "Лукьяненко";

    private static final Author existingAUTHOR1 = new Author(EXISTING_AUTHOR_ID1, EXISTING_AUTHOR_FIRSTNAME1, EXISTING_AUTHOR_LASTNAME1);
    private static final Genre existingGENRE1 = new Genre(EXISTING_GENRE_ID1, EXISTING_GENRE_NAME1);
    private static final Author existingAUTHOR2 = new Author(EXISTING_AUTHOR_ID2, EXISTING_AUTHOR_FIRSTNAME2, EXISTING_AUTHOR_LASTNAME2);

    private static final Book existingBOOK1 = new Book(EXISTING_BOOK_ID1, EXISTING_BOOK_TITLE1, existingAUTHOR1, existingGENRE1);
    private static final Book existingBOOK2 = new Book(EXISTING_BOOK_ID2, EXISTING_BOOK_TITLE2, existingAUTHOR2, existingGENRE1);

    private static final String NEW_BOOK_ID = "99";
    private static final String NEW_BOOK_TITLE = "Title";


    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private ObjectMapper mapper;

    @DisplayName("Возвращать список книг")
    @Test
    public void shouldReturnListBooks() throws Exception {
        val books = List.of(existingBOOK1, existingBOOK2);
        val bookFlux = Flux.fromIterable(books);
        Mockito.when(bookRepository.findAll()).thenReturn(bookFlux);

        webClient.get().uri("/api/books")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class);
    }

    @DisplayName("Возвращять книгу по id")
    @Test
    public void shouldReturnBook() throws Exception {
        Mockito.when(bookRepository.findById(EXISTING_BOOK_ID1))
                .thenReturn(Mono.just(existingBOOK1));

        webClient.get().uri("/api/books/{id}", EXISTING_BOOK_ID1)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(EXISTING_BOOK_ID1)
                .jsonPath("$.title").isEqualTo(EXISTING_BOOK_TITLE1);
    }

    @DisplayName("Создавать книгу")
    @Test
    public void shouldNewBook() throws Exception {
        val newBook = new Book(null, NEW_BOOK_TITLE, existingAUTHOR1, existingGENRE1);
        val expectedBook = new Book(EXISTING_BOOK_ID1, NEW_BOOK_TITLE, existingAUTHOR1, existingGENRE1);

        Mockito.when(authorRepository.findById(EXISTING_AUTHOR_ID1))
                .thenReturn(Mono.just(existingAUTHOR1));

        Mockito.when(genreRepository.findById(EXISTING_GENRE_ID1))
                .thenReturn(Mono.just(existingGENRE1));

        Mockito.when(bookRepository.save(newBook))
                .thenReturn(Mono.just(expectedBook));

        webClient.post().uri("/api/books/?title={title}&authorId={authorId}&genreId={genreId}", NEW_BOOK_TITLE, EXISTING_AUTHOR_ID1, EXISTING_GENRE_ID1)
                .exchange()
                .expectStatus().isOk();
    }

    @DisplayName("Сохранять книгу")
    @Test
    public void shouldSaveBook() throws Exception {
        val newBook = new Book(EXISTING_BOOK_ID1, NEW_BOOK_TITLE, existingAUTHOR2, existingGENRE1);
        Mockito.when(authorRepository.findById(EXISTING_AUTHOR_ID2))
                .thenReturn(Mono.just(existingAUTHOR2));

        Mockito.when(genreRepository.findById(EXISTING_GENRE_ID1))
                .thenReturn(Mono.just(existingGENRE1));

        Mockito.when(bookRepository.save(newBook))
                .thenReturn(Mono.just(newBook));

        webClient.put().uri("/api/books/?id={id}&title={title}&authorId={authorId}&genreId={genreId}", EXISTING_BOOK_ID1, NEW_BOOK_TITLE, EXISTING_AUTHOR_ID2, EXISTING_GENRE_ID1)
                .exchange()
                .expectStatus().isOk();
    }

    @DisplayName("Удалять книгу")
    @Test
    public void shouldDeleteBook() throws Exception {
        webClient.delete().uri("/api/books/{id}", EXISTING_BOOK_ID1)
                .exchange()
                .expectStatus().isOk();
    }

}
