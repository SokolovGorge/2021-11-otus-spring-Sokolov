package ru.otus.ormlibrary.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.ormlibrary.models.Book;
import ru.otus.ormlibrary.models.Remark;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с примечаниями должен")
@DataJpaTest
@Import(RemarkRepositoryJpa.class)
class RemarkRepositoryJpaTest {

    private static final long EXISTING_BOOK_ID = 1;
    private static final int EXPECTED_REMARK_COUNT = 4;
    private static final long EXISTING_REMARK_ID = 1;

    @Autowired
    private RemarkRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Возвращать ожидаемый список примечаний")
    @Test
    void shouldReturnExpectedRemarkList() {
        val remarks = repositoryJpa.findAllByBook(EXISTING_BOOK_ID);
        assertThat(remarks).isNotNull().hasSize(EXPECTED_REMARK_COUNT);
    }

    @DisplayName("Сохранить примечание в БД")
    @Test
    void shouldSaveRemark() {
        val existingBook = em.find(Book.class, EXISTING_BOOK_ID);
        val newRemark = new Remark(null, existingBook, "Тест");

        var expectedRemark = repositoryJpa.save(newRemark);
        var actualRemark = repositoryJpa.findById(expectedRemark.getId()).get();
        assertThat(actualRemark).usingRecursiveComparison().isEqualTo(actualRemark);

        val updatingRemark = repositoryJpa.findById(EXISTING_REMARK_ID).get();
        updatingRemark.setText("Test");
        expectedRemark = repositoryJpa.save(updatingRemark);
        em.flush();
        actualRemark = repositoryJpa.findById(expectedRemark.getId()).get();
        assertThat(actualRemark).usingRecursiveComparison().isEqualTo(actualRemark);
    }

    @DisplayName("Удалить заданное примечание по id")
    @Test
    void shouldCorrectDeleteRemarkById() {
        val optionalExistingRemark = repositoryJpa.findById(EXISTING_REMARK_ID);
        assertThat(optionalExistingRemark.isPresent());
        repositoryJpa.deleteById(EXISTING_REMARK_ID);
        val optionalDeletingRemark = repositoryJpa.findById(EXISTING_REMARK_ID);
        assertThat(optionalDeletingRemark.isEmpty());
    }

    @DisplayName("Возвращать примечание по id")
    @Test
    void shouldFindExpectedRemarkById() {
        val optionalActualRemark = repositoryJpa.findById(EXISTING_REMARK_ID);
        val expectedRemark = em.find(Remark.class, EXISTING_REMARK_ID);
        assertThat(optionalActualRemark).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedRemark);
    }
}
