package ru.otus.jpalibrary.repository;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Data Jpa для работы с книгами должен")
@DataJpaTest
class BookRepositoryTest {

    private static final int EXPECTED_BOOK_COUNT = 2;
    private static final int EXPECTED_QUERIES_COUNT = 1;
    private static final String EXISTING_TITLE = "Императоры иллюзий";
    private static final String EXISTING_AUTHOR_SURNAME = "Кристи";
    private static final String EXISTING_GENRE_NAME = "Детектив";

    @Autowired
    private BookRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val books = repository.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_BOOK_COUNT);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("Возвращать книги по заголовку")
    @Test
    void shouldReturnBookByTitle() {
        val books = repository.findByTitle(EXISTING_TITLE);
        assertThat(books).isNotNull().isNotEmpty();
    }

    @DisplayName("Возвращать книги по фамилии автора")
    @Test
    void shouldReturnBookByAuthorSurName() {
        val books = repository.findByAuthorSurName(EXISTING_AUTHOR_SURNAME);
        assertThat(books).isNotNull().isNotEmpty();
    }

    @DisplayName("Возвращать книги по названию жанра")
    @Test
    void shouldReturnBookByGenreName() {
        val books = repository.findByGenreName(EXISTING_GENRE_NAME);
        assertThat(books).isNotNull().isNotEmpty();
    }
}
