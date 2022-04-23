package ru.otus.restlibrary.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.otus.restlibrary.domain.Author;
import ru.otus.restlibrary.domain.Book;
import ru.otus.restlibrary.domain.Genre;
import ru.otus.restlibrary.dto.BookDto;
import ru.otus.restlibrary.service.BookService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер книг должен")
@WebMvcTest(BookController.class)
class BookControllerTest {


    private static final long EXISTING_BOOK_ID1 = 1;
    private static final String EXISTING_BOOK_TITLE1 = "Восточный экспресс";
    private static final long EXISTING_BOOK_ID2 = 2;
    private static final String EXISTING_BOOK_TITLE2 = "Императоры иллюзий";
    private static final long EXISTING_GENRE_ID1 = 1;
    private static final String EXISTING_GENRE_NAME1 = "Детектив";
    private static final long EXISTING_AUTHOR_ID1 = 1;
    private static final String EXISTING_AUTHOR_FIRSTNAME1 = "Агата";
    private static final String EXISTING_AUTHOR_LASTNAME1 = "Кристи";
    private static final long EXISTING_AUTHOR_ID2 = 2;
    private static final String EXISTING_AUTHOR_FIRSTNAME2 = "Сергей";
    private static final String EXISTING_AUTHOR_LASTNAME2 = "Лукьяненко";

    private static final Author existingAUTHOR1 = new Author(EXISTING_AUTHOR_ID1, EXISTING_AUTHOR_FIRSTNAME1, EXISTING_AUTHOR_LASTNAME1);
    private static final Genre existingGENRE1 = new Genre(EXISTING_GENRE_ID1, EXISTING_GENRE_NAME1);
    private static final Author existingAUTHOR2 = new Author(EXISTING_AUTHOR_ID2, EXISTING_AUTHOR_FIRSTNAME2, EXISTING_AUTHOR_LASTNAME2);

    private static final Book existingBOOK1 = new Book(EXISTING_BOOK_ID1, EXISTING_BOOK_TITLE1, existingAUTHOR1, existingGENRE1);
    private static final Book existingBOOK2 = new Book(EXISTING_BOOK_ID2, EXISTING_BOOK_TITLE2, existingAUTHOR2, existingGENRE1);

    private static final long NEW_BOOK_ID = 99;
    private static final String NEW_BOOK_TITLE = "Title";


    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @DisplayName("Возвращять список книг")
    @Test
    public void shouldReturnListBooks() throws Exception {
        List<BookDto> expectedResult = List.of(new BookDto(existingBOOK1), new BookDto(existingBOOK2));
        given(bookService.getAllBooks()).willReturn(expectedResult);

        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @DisplayName("Возвращять книгу по id")
    @Test
    public void shouldReturnBook() throws Exception {
        BookDto expectedResult = new BookDto(existingBOOK1);
        given(bookService.getBook(EXISTING_BOOK_ID1)).willReturn(expectedResult);

        mvc.perform(get("/api/books/" + String.valueOf(EXISTING_BOOK_ID1)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }
    @DisplayName("Создавать книгу")
    @Test
    public void shouldNewBook() throws Exception {
        given(bookService.addBook(NEW_BOOK_TITLE, EXISTING_AUTHOR_ID2, EXISTING_GENRE_ID1))
                .willReturn(new BookDto(new Book(NEW_BOOK_ID, NEW_BOOK_TITLE, existingAUTHOR2, existingGENRE1)));
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("title", NEW_BOOK_TITLE);
        map.add("authorId", String.valueOf(EXISTING_AUTHOR_ID2));
        map.add("genreId", String.valueOf(EXISTING_GENRE_ID1));
        mvc.perform(post("/api/books").params(map))
                .andExpect(status().isOk());
    }



    @DisplayName("Сохранять книгу")
    @Test
    public void shouldSaveBook() throws Exception {
        given(bookService.updateBook(existingBOOK1.getId(), existingBOOK1.getTitle(), existingBOOK1.getAuthor().getId(), existingBOOK1.getGenre().getId()))
                .willReturn(new BookDto(existingBOOK1));
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("id", String.valueOf(EXISTING_BOOK_ID1));
        map.add("title", EXISTING_BOOK_TITLE1);
        map.add("authorId", String.valueOf(EXISTING_AUTHOR_ID1));
        map.add("genreId", String.valueOf(EXISTING_GENRE_ID1));
        mvc.perform(put("/api/books").params(map))
                .andExpect(status().isOk());
    }

    @DisplayName("Удалять книгу")
    @Test
    public void shouldDeleteBook() throws Exception {
        given(bookService.deleteBook(EXISTING_BOOK_ID1)).willReturn(new BookDto(existingBOOK1));
        mvc.perform(delete("/api/books/" + String.valueOf(EXISTING_BOOK_ID1)))
                .andExpect(status().isOk());
    }

}
