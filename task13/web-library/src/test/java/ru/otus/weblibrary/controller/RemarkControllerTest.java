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
import ru.otus.weblibrary.dto.RemarkDto;
import ru.otus.weblibrary.service.RemarkService;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Контроллер отзывов должен")
@WebMvcTest(RemarkController.class)
class RemarkControllerTest {

    private static final long EXISTING_BOOK_ID1 = 1;
    private static final String EXISTING_REMARK_TEXT1 = "Отзыв1";
    private static final String EXISTING_REMARK_TEXT2 = "Отзыв2";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RemarkService remarkService;

    @MockBean
    private UserDetailsService userDetailsService;


    @DisplayName("Возвращять список отзывов")
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void shouldReturnListRemarks() throws Exception {
        val remarks = Arrays.asList(new RemarkDto(1L, EXISTING_REMARK_TEXT1), new RemarkDto(2L, EXISTING_REMARK_TEXT2));
        given(userDetailsService.loadUserByUsername("admin")).willReturn(User.withUsername("admin").password("pass").authorities("ROLE_ADMIN").build());
        given(remarkService.getAllRemarksByBook(EXISTING_BOOK_ID1)).willReturn(remarks);
        mvc.perform(get("/remarklist?bookId=" + EXISTING_BOOK_ID1))
                .andExpect(status().isOk())
                .andExpect(content().string(new StringContains(EXISTING_REMARK_TEXT1)));
    }

    @DisplayName("Перевод на страницу логина со страницы списка отзывов")
    @Test
    public void shouldNotReturnListRemarks() throws Exception {
        val remarks = Arrays.asList(new RemarkDto(1L, EXISTING_REMARK_TEXT1), new RemarkDto(2L, EXISTING_REMARK_TEXT2));
        given(userDetailsService.loadUserByUsername("admin")).willReturn(User.withUsername("admin").password("pass").authorities("ROLE_ADMIN").build());
        given(remarkService.getAllRemarksByBook(EXISTING_BOOK_ID1)).willReturn(remarks);
        mvc.perform(get("/remarklist?bookId=" + EXISTING_BOOK_ID1))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("Создание отзыва")
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void shouldReturnNewRemarks() throws Exception {
        given(userDetailsService.loadUserByUsername("admin")).willReturn(User.withUsername("admin").password("pass").authorities("ROLE_ADMIN").build());
        mvc.perform(get("/remarknew?bookId=" + EXISTING_BOOK_ID1))
                .andExpect(status().isOk())
                .andExpect(content().string(new StringContains("<title>Новый комментарий</title>")));
    }

    @DisplayName("Перевод на страницу логина со страницы создание отзыва")
    @Test
    public void shouldNotReturnNewRemarks() throws Exception {
        given(userDetailsService.loadUserByUsername("admin")).willReturn(User.withUsername("admin").password("pass").authorities("ROLE_ADMIN").build());
        mvc.perform(get("/remarknew?bookId=" + EXISTING_BOOK_ID1))
                .andExpect(status().is3xxRedirection());
    }

}
