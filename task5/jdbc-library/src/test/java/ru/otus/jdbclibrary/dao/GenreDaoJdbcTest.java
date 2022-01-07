package ru.otus.jdbclibrary.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.jdbclibrary.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static final long EXPECTED_GENRE_COUNT = 2;
    private static final long EXISTING_GENRE_ID = 1;
    private static final String EXISTING_GENRE_NAME = "Детектив";

    private static final Genre existingGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);

    @Autowired
    private GenreDaoJdbc genreDao;

    @DisplayName("Возвращает ожидаемое кол-во жанров в БД")
    @Test
    void shouldReturnExpectedGenreCount() {
        long actualAuthorsCount = genreDao.count();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_GENRE_COUNT);
    }

    @DisplayName("Добавить жанр в БД")
    @Test
    void shouldInsertGenre() {
        Genre expectedGenre = new Genre(null, "Эксклюзив");
        long id = genreDao.insert(expectedGenre);
        expectedGenre.setId(id);
        Genre actualGenre = genreDao.getById(id);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("Обновить жанр в БД")
    @Test
    void shouldUpdateGenre() {
        Genre updatingGenre = new Genre(EXISTING_GENRE_ID, "Чтиво");
        genreDao.update(updatingGenre);
        Genre actualGenre = genreDao.getById(EXISTING_GENRE_ID);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(updatingGenre);
    }

    @DisplayName("Возвращать жанр по id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre actualGenre = genreDao.getById(EXISTING_GENRE_ID);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(existingGenre);
    }

    @DisplayName("Возвращать ожидаемый список жанров")
    @Test
    void shouldReturnExpectedGenreList() {
        List<Genre> actualGenreList = genreDao.getAll();
        assertThat(actualGenreList).contains(existingGenre);
    }

    @DisplayName("Удалить заданный жанр по id")
    @Test
    void shouldCorrectDeleteAuthorById() {
        assertThatCode(() -> genreDao.getById(EXISTING_GENRE_ID))
                .doesNotThrowAnyException();
        genreDao.deleteById(EXISTING_GENRE_ID);
        assertThatCode(() -> genreDao.getById(EXISTING_GENRE_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
