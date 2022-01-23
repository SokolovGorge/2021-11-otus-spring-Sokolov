package ru.otus.mongolibrary.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Data MongoDB для работы с примечаниями книги должен")
@DataMongoTest
@ComponentScan({ "ru.otus.example.mongolibrary.repository"})
class RemarkRepositoryTest {

    private static final int EXPECTED_ALL_REMARK_COUNT = 20;
    private static final int EXPECTED_BOOK_REMARK_COUNT = 4;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RemarkRepository remarkRepository;

    @DisplayName("Возвращать ожидаемый список всех примечаний")
    @Test
    void shouldReturnAllRemarks() {
        val remarks = remarkRepository.findAll();
        assertThat(remarks).isNotNull().hasSize(EXPECTED_ALL_REMARK_COUNT);
    }

    @DisplayName("Возвращать ожидаемый список примечаний книги")
    @Test
    void shouldReturnExpectedRemarkList() {
        val books = bookRepository.findAll();
        val remarks = remarkRepository.findAllRemarksByBook(books.get(0));
        assertThat(remarks).isNotNull().hasSize(EXPECTED_BOOK_REMARK_COUNT);
    }

}
