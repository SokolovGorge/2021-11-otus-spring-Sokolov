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
import ru.otus.weblibrary.dto.AuthorDto;
import ru.otus.weblibrary.dto.BookDto;
import ru.otus.weblibrary.service.AuthorService;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер книг должен")
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    private static final long EXISTING_AUTHOR_ID1 = 1;
    private static final String EXISTING_AUTHOR_FIRSTNAME1 = "Агата";
    private static final String EXISTING_AUTHOR_LASTNAME1 = "Кристи";
    private static final long EXISTING_AUTHOR_ID2 = 2;
    private static final String EXISTING_AUTHOR_FIRSTNAME2 = "Сергей";
    private static final String EXISTING_AUTHOR_LASTNAME2 = "Лукьяненко";

    private static final Author existingAUTHOR1 = new Author(EXISTING_AUTHOR_ID1, EXISTING_AUTHOR_FIRSTNAME1, EXISTING_AUTHOR_LASTNAME1);
    private static final Author existingAUTHOR2 = new Author(EXISTING_AUTHOR_ID2, EXISTING_AUTHOR_FIRSTNAME2, EXISTING_AUTHOR_LASTNAME2);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private UserDetailsService userDetailsService;

    @DisplayName("Возвращять список авторов для админа")
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void shouldReturnListAuthorByAdmin() throws Exception {
        given(userDetailsService.loadUserByUsername("admin")).willReturn(User.withUsername("admin").password("pass").authorities("ROLE_ADMIN").build());
        val authors = Arrays.asList(new AuthorDto(existingAUTHOR1), new AuthorDto(existingAUTHOR2));
        given(authorService.getAllAuthors()).willReturn(authors);
        mvc.perform(get("/authorlist"))
                .andExpect(status().isOk())
                .andExpect(content().string(new StringContains(EXISTING_AUTHOR_FIRSTNAME1)));
    }

    @DisplayName("Возвращать ошибку 403 для юзера")
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    public void shouldReturnListAuthorByUser() throws Exception {
        given(userDetailsService.loadUserByUsername("user")).willReturn(User.withUsername("user").password("password").authorities("ROLE_USER").build());
        val authors = Arrays.asList(new AuthorDto(existingAUTHOR1), new AuthorDto(existingAUTHOR2));
        given(authorService.getAllAuthors()).willReturn(authors);
        mvc.perform(get("/authorlist"))
                .andExpect(status().isForbidden());
    }

}
