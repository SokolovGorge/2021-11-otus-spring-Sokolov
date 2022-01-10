package ru.otus.ormlibrary.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.ormlibrary.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами должен")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    private static final int EXPECTED_GENRE_COUNT = 2;
    private static final long EXISTING_GENRE_ID1 = 1;
    private static final long EXISTING_GENRE_ID2 = 2;


    @Autowired
    private GenreRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Возвращает ожидаемое кол-во жанров в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        val actualGenreCount = repositoryJpa.count();
        assertThat(actualGenreCount).isEqualTo(EXPECTED_GENRE_COUNT);
    }

    @DisplayName("Сохранить жанр в БД")
    @Test
    void shouldSaveGenre() {
        val newGenre = new Genre(null, "Жанр");
        var expectedGenre = repositoryJpa.save(newGenre);
        var actualGenre = repositoryJpa.findById(expectedGenre.getId()).get();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);

        val updatingGenre = repositoryJpa.findById(EXISTING_GENRE_ID1).get();
        updatingGenre.setName("Test");
        expectedGenre = repositoryJpa.save(updatingGenre);
        actualGenre = repositoryJpa.findById(EXISTING_GENRE_ID1).get();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("Удалить заданного жанр по id")
    @Test
    void shouldCorrectDeleteGenreById() {
        val optionalExistingGenre = repositoryJpa.findById(EXISTING_GENRE_ID2);
        assertThat(optionalExistingGenre.isPresent());
        repositoryJpa.deleteById(EXISTING_GENRE_ID2);
        val optionalDeletingGenre = repositoryJpa.findById(EXISTING_GENRE_ID2);
        assertThat(optionalDeletingGenre.isEmpty());
    }

    @DisplayName("Возвращать жанр по id")
    @Test
    void shouldFindExpectedGenreById() {
        val optionalActualGenre = repositoryJpa.findById(EXISTING_GENRE_ID1);
        val expectedGenre = em.find(Genre.class, EXISTING_GENRE_ID1);
        assertThat(optionalActualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("Возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedGenreList() {
        val authors = repositoryJpa.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_GENRE_COUNT);
    }
}
