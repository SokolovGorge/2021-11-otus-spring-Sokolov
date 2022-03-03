package ru.otus.weblibrary.controller;

import lombok.val;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.weblibrary.domain.Author;
import ru.otus.weblibrary.domain.Book;
import ru.otus.weblibrary.domain.Genre;
import ru.otus.weblibrary.dto.BookDto;
import ru.otus.weblibrary.service.AuthorService;
import ru.otus.weblibrary.service.BookService;
import ru.otus.weblibrary.service.GenreService;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private UserDetailsService userDetailsService;

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )

    @DisplayName("Возвращять список книг")
    @Test
    public void shouldReturnListBooks() throws Exception {
        given(userDetailsService.loadUserByUsername("admin")).willReturn(User.withUsername("admin").password("pass").authorities("ROLE_ADMIN").build());
        val books = Arrays.asList(new BookDto(existingBOOK1), new BookDto(existingBOOK2));
        given(bookService.getAllBooks()).willReturn(books);
        mvc.perform(get("/booklist"))
                .andExpect(status().isOk())
               .andExpect(content().string(new StringContains(EXISTING_BOOK_TITLE1)));
    }

    @DisplayName("Редактировать книгу")
    @Test
    public void shouldReturnEditBooks() throws Exception {
        given(userDetailsService.loadUserByUsername("admin")).willReturn(User.withUsername("admin").password("pass").authorities("ROLE_ADMIN").build());
        given(bookService.getBook(EXISTING_BOOK_ID1)).willReturn(new BookDto(existingBOOK1));
        mvc.perform(get("/bookedit?id=" + EXISTING_BOOK_ID1))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("Создать книгу")
    @Test
    public void shouldReturnNewBooks() throws Exception {
        given(userDetailsService.loadUserByUsername("admin")).willReturn(User.withUsername("admin").password("pass").authorities("ROLE_ADMIN").build());
        mvc.perform(get("/booknew"))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("Удалить книгу")
    @Test
    public void shouldReturnDeleteBooks() throws Exception {
        given(userDetailsService.loadUserByUsername("admin")).willReturn(User.withUsername("admin").password("pass").authorities("ROLE_ADMIN").build());
        given(bookService.getBook(EXISTING_BOOK_ID1)).willReturn(new BookDto(existingBOOK1));
        mvc.perform(get("/bookdelete?id=" + EXISTING_BOOK_ID1))
                .andExpect(status().is3xxRedirection());
    }
}
