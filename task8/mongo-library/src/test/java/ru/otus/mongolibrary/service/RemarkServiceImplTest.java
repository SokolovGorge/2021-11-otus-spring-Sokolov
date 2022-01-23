package ru.otus.mongolibrary.service;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.mongolibrary.domain.Author;
import ru.otus.mongolibrary.domain.Book;
import ru.otus.mongolibrary.domain.Genre;
import ru.otus.mongolibrary.domain.Remark;
import ru.otus.mongolibrary.dto.RemarkDto;
import ru.otus.mongolibrary.exceptions.ApplicationException;
import ru.otus.mongolibrary.repository.BookRepository;
import ru.otus.mongolibrary.repository.RemarkRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("Тестирование сервиса примечаний")
@SpringBootTest
class RemarkServiceImplTest {
    private static final String EXISTING_BOOK_ID1 = "b1";
    private static final String EXISTING_BOOK_TITLE1 = "Восточный экспресс";
    private static final String EXISTING_GENRE_ID1 = "g1";
    private static final String EXISTING_GENRE_NAME1 = "Детектив";
    private static final String EXISTING_AUTHOR_ID1 = "a1";
    private static final String EXISTING_AUTHOR_FIRSTNAME1 = "Агата";
    private static final String EXISTING_AUTHOR_LASTNAME1 = "Кристи";
    private static final String EXISTING_REMARK_ID1 = "r1";
    private static final String EXISTING_REMARK_TEXT1 = "Примечание 11";
    private static final String EXISTING_REMARK_TEXT2 = "Примечание 12";
    private static final String NOT_EXISTING_ID = "99";

    private static final Author existingAUTHOR1 = new Author(EXISTING_AUTHOR_ID1, EXISTING_AUTHOR_FIRSTNAME1, EXISTING_AUTHOR_LASTNAME1);
    private static final Genre existingGENRE1 = new Genre(EXISTING_GENRE_ID1, EXISTING_GENRE_NAME1);

    private static final Book existingBOOK = new Book(EXISTING_BOOK_ID1, EXISTING_BOOK_TITLE1, existingAUTHOR1, existingGENRE1);
    private static final Remark existingRemark1 = new Remark(EXISTING_REMARK_ID1, existingBOOK, EXISTING_REMARK_TEXT1);
    private static final Remark existingRemark2 = new Remark(EXISTING_REMARK_ID1, existingBOOK, EXISTING_REMARK_TEXT2);


    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private RemarkRepository remarkRepository;

    @Autowired
    private RemarkService remarkService;

    @DisplayName("Возвращает список примечаний книги")
    @Test
    void shouldReturnAllRemarksByBook() {
        given(bookRepository.findById(EXISTING_BOOK_ID1)).willReturn(Optional.of(existingBOOK));
        given(remarkRepository.findAllRemarksByBook(existingBOOK)).willReturn(List.of(existingRemark1, existingRemark2));
        val actualRemarks = remarkService.getAllRemarksByBook(EXISTING_BOOK_ID1);
        assertThat(actualRemarks).containsExactlyInAnyOrder(new RemarkDto(existingRemark1), new RemarkDto(existingRemark2));
    }

    @DisplayName("Добавляет примечание")
    @Test
    void shouldAddRemark() {
        String newId = "10";
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
