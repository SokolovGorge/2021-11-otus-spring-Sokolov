package ru.otus.weblibrary.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Data Jpa для работы с примечаниями книги должен")
@DataJpaTest
class RemarkRepositoryTest {

    private static final long EXISTING_BOOK_ID = 1;
    private static final int EXPECTED_REMARK_COUNT = 4;


    @Autowired
    private RemarkRepository repository;

    @DisplayName("Возвращать ожидаемый список примечаний")
    @Test
    void shouldReturnExpectedRemarkList() {
        val remarks = repository.findAllRemarksByBookId(EXISTING_BOOK_ID);
        assertThat(remarks).isNotNull().hasSize(EXPECTED_REMARK_COUNT);
    }
}
