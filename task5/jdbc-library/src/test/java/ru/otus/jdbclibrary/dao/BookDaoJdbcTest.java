package ru.otus.jdbclibrary.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.jdbclibrary.domain.Author;
import ru.otus.jdbclibrary.domain.Book;
import ru.otus.jdbclibrary.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;


@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static final long EXPECTED_BOOK_COUNT = 1;
    private static final long EXISTING_BOOK_ID = 1;
    private static final String EXISTING_BOOK_TITLE = "Восточный экспресс";
    private static final long EXISTING_GENRE_ID1 = 1;
    private static final String EXISTING_GENRE_NAME1 = "Детектив";
    private static final long EXISTING_AUTHOR_ID1 = 1;
    private static final String EXISTING_AUTHOR_FIRSTNAME1 = "Агата";
    private static final String EXISTING_AUTHOR_LASTNAME1 = "Кристи";
    private static final long EXISTING_GENRE_ID2 = 2;
    private static final String EXISTING_GENRE_NAME2 = "Фантастика";
    private static final long EXISTING_AUTHOR_ID2 = 2;
    private static final String EXISTING_AUTHOR_FIRSTNAME2 = "Сергей";
    private static final String EXISTING_AUTHOR_LASTNAME2 = "Лукьяненко";

    private static final Author existingAuthor1 = new Author(EXISTING_AUTHOR_ID1, EXISTING_AUTHOR_FIRSTNAME1, EXISTING_AUTHOR_LASTNAME1);
    private static final Genre existingGenre1 = new Genre(EXISTING_GENRE_ID1, EXISTING_GENRE_NAME1);
    private static final Author existingAuthor2 = new Author(EXISTING_AUTHOR_ID2, EXISTING_AUTHOR_FIRSTNAME2, EXISTING_AUTHOR_LASTNAME2);
    private static final Genre existingGenre2 = new Genre(EXISTING_GENRE_ID2, EXISTING_GENRE_NAME2);
    private static final Book existingBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE, existingAuthor1, existingGenre1);

    @Autowired
    private BookDaoJdbc bookDao;

    @DisplayName("Возвращает ожидаемое кол-во книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        long actualBooksCount = bookDao.count();
        assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("Добавить книгу в БД")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(null,
                "Поваренная книга",
                existingAuthor1,
                existingGenre1);
        long id = bookDao.insert(expectedBook);
        expectedBook.setId(id);
        Book actualBook = bookDao.getById(id);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Обновить книгу в БД")
    @Test
    void shouldUpdateBook() {
        Book updatingBook = new Book(EXISTING_BOOK_ID, "Книга", existingAuthor2, existingGenre2);
        bookDao.update(updatingBook);
        Book actualBook = bookDao.getById(EXISTING_BOOK_ID);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(updatingBook);
    }

    @DisplayName("Возвращать книгу по id")
    @Test
    void shouldReturnExpectedBookById() {
        Book actualBook = bookDao.getById(EXISTING_BOOK_ID);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(existingBook);
    }

    @DisplayName("Возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        List<Book> actualBookList = bookDao.getAll();
        assertThat(actualBookList).containsExactlyInAnyOrder(existingBook);
    }

    @DisplayName("Удалить заданную книгу по id")
    @Test
    void shouldCorrectDeleteBookById() {
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();
        bookDao.deleteById(EXISTING_BOOK_ID);
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

}
