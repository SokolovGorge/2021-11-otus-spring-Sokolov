package ru.otus.weblibrary.service;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.otus.weblibrary.domain.Author;
import ru.otus.weblibrary.domain.Book;
import ru.otus.weblibrary.domain.Genre;
import ru.otus.weblibrary.domain.Remark;
import ru.otus.weblibrary.dto.RemarkDto;
import ru.otus.weblibrary.exceptions.ApplicationException;
import ru.otus.weblibrary.repository.BookRepository;
import ru.otus.weblibrary.repository.RemarkRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@DisplayName("Тестирование сервиса примечаний")
@WebMvcTest(RemarkServiceImpl.class)
class RemarkServiceImplTest {
    private static final long EXISTING_BOOK_ID1 = 1;
    private static final String EXISTING_BOOK_TITLE1 = "Восточный экспресс";
    private static final long EXISTING_GENRE_ID1 = 1;
    private static final String EXISTING_GENRE_NAME1 = "Детектив";
    private static final long EXISTING_AUTHOR_ID1 = 1;
    private static final String EXISTING_AUTHOR_FIRSTNAME1 = "Агата";
    private static final String EXISTING_AUTHOR_LASTNAME1 = "Кристи";
    private static final long EXISTING_REMARK_ID1 = 1;
    private static final String EXISTING_REMARK_TEXT1 = "Примечание 11";
    private static final String EXISTING_REMARK_TEXT2 = "Примечание 12";
    private static final long NOT_EXISTING_ID = 99;

    private static final Author existingAUTHOR1 = new Author(EXISTING_AUTHOR_ID1, EXISTING_AUTHOR_FIRSTNAME1, EXISTING_AUTHOR_LASTNAME1);
    private static final Genre existingGENRE1 = new Genre(EXISTING_GENRE_ID1, EXISTING_GENRE_NAME1);

    private static final Book existingBOOK = new Book(EXISTING_BOOK_ID1, EXISTING_BOOK_TITLE1, existingAUTHOR1, existingGENRE1);
    private static final Remark existingRemark1 = new Remark(EXISTING_REMARK_ID1, existingBOOK, EXISTING_REMARK_TEXT1);
    private static final Remark existingRemark2 = new Remark(EXISTING_REMARK_ID1, existingBOOK, EXISTING_REMARK_TEXT2);


    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private RemarkRepository remarkRepository;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private RemarkService remarkService;

    @DisplayName("Возвращает список примечаний книги")
    @Test
    void shouldReturnAllRemarksByBook() {
        given(remarkRepository.findAllRemarksByBookId(EXISTING_BOOK_ID1)).willReturn(List.of(existingRemark1, existingRemark2));
        val actualRemarks = remarkService.getAllRemarksByBook(EXISTING_BOOK_ID1);
        assertThat(actualRemarks).containsExactlyInAnyOrder(new RemarkDto(existingRemark1), new RemarkDto(existingRemark2));
    }

    @DisplayName("Добавляет примечание")
    @Test
    void shouldAddRemark() {
        long newId = 10;
        val newText = "Примечание";
        val newRemark = new Remark(newId, existingBOOK, newText);
        given(remarkRepository.save(new Remark(null, existingBOOK, newText)))
                .willReturn(newRemark);
        given(bookRepository.findById(EXISTING_BOOK_ID1)).willReturn(Optional.of(existingBOOK));
        val expectedRemarkDto = new RemarkDto(newRemark);

        val actualRemarkDto = remarkService.addRemark(EXISTING_BOOK_ID1, newText);
        assertThat(actualRemarkDto).usingRecursiveComparison().isEqualTo(expectedRemarkDto);
        assertThrows(ApplicationException.class, () -> remarkService.addRemark(NOT_EXISTING_ID, newText));
    }

}
