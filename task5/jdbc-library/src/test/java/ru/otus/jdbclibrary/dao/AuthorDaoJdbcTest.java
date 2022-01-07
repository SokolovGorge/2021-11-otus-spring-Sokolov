package ru.otus.jdbclibrary.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.jdbclibrary.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с авторами должно")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    private static final long EXPECTED_AUTHOR_COUNT = 2;
    private static final long EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_AUTHOR_FIRSTNAME = "Агата";
    private static final String EXISTING_AUTHOR_LASTNAME = "Кристи";

    private static final Author existingAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FIRSTNAME, EXISTING_AUTHOR_LASTNAME);

    @Autowired
    private AuthorDaoJdbc authorDao;

    @DisplayName("Возвращает ожидаемое кол-во авторов в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        long actualAuthorsCount = authorDao.count();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_AUTHOR_COUNT);
    }

    @DisplayName("Добавить автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author(null, "Вася", "Васечкин");
        long id = authorDao.insert(expectedAuthor);
        expectedAuthor.setId(id);
        Author actualAuthor = authorDao.getById(id);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("Обновить автора в БД")
    @Test
    void shouldUpdateAuthor() {
        Author updatingAuthor = new Author(EXISTING_AUTHOR_ID, "Вася", "Васечкин");
        authorDao.update(updatingAuthor);
        Author actualAuthor = authorDao.getById(EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(updatingAuthor);
    }

    @DisplayName("Возвращать автора по id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author actualAuthor = authorDao.getById(EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(existingAuthor);
    }

    @DisplayName("Возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorList() {
        List<Author> actualAuthorList = authorDao.getAll();
        assertThat(actualAuthorList).contains(existingAuthor);
    }

    @DisplayName("Удалить заданного автора по id")
    @Test
    void shouldCorrectDeleteAuthorById() {
        assertThatCode(() -> authorDao.getById(EXISTING_AUTHOR_ID))
                .doesNotThrowAnyException();
        authorDao.deleteById(EXISTING_AUTHOR_ID);
        assertThatCode(() -> authorDao.getById(EXISTING_AUTHOR_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
