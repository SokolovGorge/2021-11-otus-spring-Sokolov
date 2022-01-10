package ru.otus.ormlibrary.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.ormlibrary.models.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами должен")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    private static final int EXPECTED_AUTHOR_COUNT = 2;
    private static final long EXISTING_AUTHOR_ID1 = 1;
    private static final long EXISTING_AUTHOR_ID2 = 2;

    @Autowired
    private AuthorRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Возвращает ожидаемое кол-во авторов в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        val actualAuthorsCount = repositoryJpa.count();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_AUTHOR_COUNT);
    }

    @DisplayName("Сохранить автора в БД")
    @Test
    void shouldSaveAuthor() {
        val newAuthor = new Author(null, "Вася", "Васечкин");
        var expectedAuthor = repositoryJpa.save(newAuthor);
        var actualAuthor = repositoryJpa.findById(expectedAuthor.getId()).get();
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);

        val updatingAuthor = repositoryJpa.findById(EXISTING_AUTHOR_ID1).get();
        updatingAuthor.setSurName("Test");
        expectedAuthor = repositoryJpa.save(updatingAuthor);
        actualAuthor = repositoryJpa.findById(EXISTING_AUTHOR_ID1).get();
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("Удалить заданного автора по id")
    @Test
    void shouldCorrectDeleteAuthorById() {
        val optionalExistingAuthor = repositoryJpa.findById(EXISTING_AUTHOR_ID2);
        assertThat(optionalExistingAuthor.isPresent());
        repositoryJpa.deleteById(EXISTING_AUTHOR_ID2);
        val optionalDeletingAuthor = repositoryJpa.findById(EXISTING_AUTHOR_ID2);
        assertThat(optionalDeletingAuthor.isEmpty());
    }

    @DisplayName("Возвращать автора по id")
    @Test
    void shouldFindExpectedAuthorById() {
        val optionalActualAuthor = repositoryJpa.findById(EXISTING_AUTHOR_ID1);
        val expectedAuthor = em.find(Author.class, EXISTING_AUTHOR_ID1);
        assertThat(optionalActualAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("Возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorList() {
        val authors = repositoryJpa.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_AUTHOR_COUNT);
    }
}
