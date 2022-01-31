package ru.otus.weblibrary.controller;

import lombok.val;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @DisplayName("Возвращять список отзывов")
    @Test
    public void shouldReturnListRemarks() throws Exception {
        val remarks = Arrays.asList(new RemarkDto(1L, EXISTING_REMARK_TEXT1), new RemarkDto(2L, EXISTING_REMARK_TEXT2));
        given(remarkService.getAllRemarksByBook(EXISTING_BOOK_ID1)).willReturn(remarks);
        mvc.perform(get("/remarklist?bookId=" + EXISTING_BOOK_ID1))
                .andExpect(status().isOk())
                .andExpect(content().string(new StringContains(EXISTING_REMARK_TEXT1)));
    }

    @DisplayName("Возвращять список отзывов")
    @Test
    public void shouldReturnNewRemarks() throws Exception {
        mvc.perform(get("/remarknew?bookId=" + EXISTING_BOOK_ID1))
                .andExpect(status().isOk())
                .andExpect(content().string(new StringContains("<title>Новый комментарий</title>")));
    }

}
