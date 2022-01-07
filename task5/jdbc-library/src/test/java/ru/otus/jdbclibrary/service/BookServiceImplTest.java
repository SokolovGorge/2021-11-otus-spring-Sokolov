package ru.otus.jdbclibrary.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.jdbclibrary.dao.AuthorDao;
import ru.otus.jdbclibrary.dao.BookDao;
import ru.otus.jdbclibrary.dao.GenreDao;
import ru.otus.jdbclibrary.domain.Author;
import ru.otus.jdbclibrary.domain.Book;
import ru.otus.jdbclibrary.domain.Genre;
import ru.otus.jdbclibrary.exceptions.ApplicationException;

import java.util.List;

import static org.mockito.BDDMockito.given;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("Тестирование сервиса книг")
@SpringBootTest
class BookServiceImplTest {

    private static final long EXISTING_BOOK_ID1 = 1;
    private static final String EXISTING_BOOK_TITLE1 = "Восточный экспресс";
    private static final long EXISTING_BOOK_ID2 = 2;
    private static final String EXISTING_BOOK_TITLE2 = "Императоры иллюзий";
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
    private static final long NOT_EXISTING_ID = 99;

    private static final Author existingAuthor1 = new Author(EXISTING_AUTHOR_ID1, EXISTING_AUTHOR_FIRSTNAME1, EXISTING_AUTHOR_LASTNAME1);
    private static final Genre existingGenre1 = new Genre(EXISTING_GENRE_ID1, EXISTING_GENRE_NAME1);
    private static final Author existingAuthor2 = new Author(EXISTING_AUTHOR_ID2, EXISTING_AUTHOR_FIRSTNAME2, EXISTING_AUTHOR_LASTNAME2);
    private static final Genre existingGenre2 = new Genre(EXISTING_GENRE_ID2, EXISTING_GENRE_NAME2);
    private static final Book existingBook1 = new Book(EXISTING_BOOK_ID1, EXISTING_BOOK_TITLE1, existingAuthor1, existingGenre1);
    private static final Book existingBook2 = new Book(EXISTING_BOOK_ID2, EXISTING_BOOK_TITLE2, existingAuthor2, existingGenre2);


    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private GenreDao genreDao;

    @MockBean
    private BookDao bookDao;

    @Autowired
    private BookService bookService;

    @DisplayName("Возвращает список книг")
    @Test
    void shouldReturnAllBooks() {
        given(bookDao.getAll()).willReturn(List.of(existingBook1, existingBook2));
        List<Book> actualBooks = bookService.getAllBooks();
        assertThat(actualBooks).containsExactlyInAnyOrder(existingBook1, existingBook2);
    }

    @DisplayName("Добавляет книгу")
    @Test
    void shouldAddBook() {
        long newId = 10;
        String newTitle = "Книга";
        Book insertBook = new Book(null, newTitle, existingAuthor1, existingGenre1);
        given(bookDao.insert(insertBook)).willReturn(newId);
        given(authorDao.getById(existingAuthor1.getId())).willReturn(existingAuthor1);
        when(authorDao.getById(NOT_EXISTING_ID)).thenThrow(new EmptyResultDataAccessException(1));
        given(genreDao.getById(existingGenre1.getId())).willReturn(existingGenre1);
        Book expectedBook = new Book(newId, newTitle, existingAuthor1, existingGenre1);

        Book actualBook = bookService.addBook(newTitle, existingAuthor1.getId(), existingGenre1.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
        assertThrows(ApplicationException.class, () -> bookService.addBook(newTitle, NOT_EXISTING_ID, existingGenre1.getId()));
    }

    @DisplayName("Изменяет книгу")
    @Test
    void shouldUpdateBook() {
        String newTitle = "Книга";
        given(authorDao.getById(existingAuthor2.getId())).willReturn(existingAuthor2);
        when(authorDao.getById(NOT_EXISTING_ID)).thenThrow(new EmptyResultDataAccessException(1));
        given(genreDao.getById(existingGenre2.getId())).willReturn(existingGenre2);
        Book expectedBook = new Book(EXISTING_BOOK_ID1, newTitle, existingAuthor2, existingGenre2);

        Book actualBook = bookService.updateBook(EXISTING_BOOK_ID1, newTitle, existingAuthor2.getId(), existingGenre2.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
        assertThrows(ApplicationException.class, () -> bookService.updateBook(EXISTING_BOOK_ID1, newTitle, NOT_EXISTING_ID, existingGenre2.getId()));
    }

    @DisplayName("Удаляет книгу")
    @Test
    void shouldDeleteBook() {
        given(bookDao.getById(EXISTING_BOOK_ID1)).willReturn(existingBook1);
        when(bookDao.getById(NOT_EXISTING_ID)).thenThrow(new EmptyResultDataAccessException(1));
        Book expectedBook = existingBook1;

        Book actualBook = bookService.deleteBook(EXISTING_BOOK_ID1);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
        assertThrows(ApplicationException.class, () -> bookService.deleteBook(NOT_EXISTING_ID));
    }

    @DisplayName("Возвращает книгу по Id")
    @Test
    void shouldGetBook() {
        given(bookDao.getById(EXISTING_BOOK_ID1)).willReturn(existingBook1);
        when(bookDao.getById(NOT_EXISTING_ID)).thenThrow(new EmptyResultDataAccessException(1));
        Book expectedBook = existingBook1;

        Book actualBook = bookService.getBook(EXISTING_BOOK_ID1);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
        assertThrows(ApplicationException.class, () -> bookService.getBook(NOT_EXISTING_ID));
    }
}
