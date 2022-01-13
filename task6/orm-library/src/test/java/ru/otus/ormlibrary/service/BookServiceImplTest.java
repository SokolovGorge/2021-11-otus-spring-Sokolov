package ru.otus.ormlibrary.service;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.ormlibrary.dto.BookDto;
import ru.otus.ormlibrary.exceptions.ApplicationException;
import ru.otus.ormlibrary.models.Author;
import ru.otus.ormlibrary.models.Book;
import ru.otus.ormlibrary.models.Genre;
import ru.otus.ormlibrary.models.Remark;
import ru.otus.ormlibrary.repositories.AuthorRepository;
import ru.otus.ormlibrary.repositories.BookRepository;
import ru.otus.ormlibrary.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

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
    private static final String EXISTING_REMARK11 = "Примечание 11";
    private static final String EXISTING_REMARK12 = "Примечание 12";
    private static final String EXISTING_REMARK13 = "Примечание 13";
    private static final String EXISTING_REMARK14 = "Примечание 14";
    private static final String EXISTING_REMARK21 = "Примечание 21";
    private static final String EXISTING_REMARK22 = "Примечание 22";
    private static final String EXISTING_REMARK23 = "Примечание 23";
    private static final String EXISTING_REMARK24 = "Примечание 24";
    private static final long NOT_EXISTING_ID = 99;

    private static final Author existingAUTHOR1 = new Author(EXISTING_AUTHOR_ID1, EXISTING_AUTHOR_FIRSTNAME1, EXISTING_AUTHOR_LASTNAME1);
    private static final Genre existingGENRE1 = new Genre(EXISTING_GENRE_ID1, EXISTING_GENRE_NAME1);
    private static final Author existingAUTHOR2 = new Author(EXISTING_AUTHOR_ID2, EXISTING_AUTHOR_FIRSTNAME2, EXISTING_AUTHOR_LASTNAME2);
    private static final Genre existingGENRE2 = new Genre(EXISTING_GENRE_ID2, EXISTING_GENRE_NAME2);

    private static final Book existingBOOK1;
    private static final Book existingBOOK2;

    static  {
        val remarks1 = new ArrayList<Remark>();
        remarks1.add(new Remark(null, null, EXISTING_REMARK11));
        remarks1.add(new Remark(null, null, EXISTING_REMARK12));
        remarks1.add(new Remark(null, null, EXISTING_REMARK13));
        remarks1.add(new Remark(null, null, EXISTING_REMARK14));
        existingBOOK1 = new Book(EXISTING_BOOK_ID1, EXISTING_BOOK_TITLE1, existingAUTHOR1, existingGENRE1, remarks1);
        val remarks2 = new ArrayList<Remark>();
        remarks2.add(new Remark(null, null, EXISTING_REMARK21));
        remarks2.add(new Remark(null, null, EXISTING_REMARK22));
        remarks2.add(new Remark(null, null, EXISTING_REMARK23));
        remarks2.add(new Remark(null, null, EXISTING_REMARK24));
        existingBOOK2 = new Book(EXISTING_BOOK_ID2, EXISTING_BOOK_TITLE2, existingAUTHOR2, existingGENRE1, remarks2);
    }

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @DisplayName("Возвращает список книг")
    @Test
    void shouldReturnAllBooks() {
        given(bookRepository.findAll()).willReturn(List.of(existingBOOK1, existingBOOK2));
        val actualBooks = bookService.getAllBooks();
        assertThat(actualBooks).containsExactlyInAnyOrder(new BookDto(existingBOOK1), new BookDto(existingBOOK2));
    }

    @DisplayName("Добавляет книгу")
    @Test
    void shouldAddBook() {
        long newId = 10;
        val newTitle = "Книга";
        val newBook = new Book(newId, newTitle, existingAUTHOR1, existingGENRE1, new ArrayList<>());
        given(bookRepository.save(new Book(null, newTitle, existingAUTHOR1, existingGENRE1, new ArrayList<>())))
                .willReturn(newBook);
        given(authorRepository.findById(EXISTING_AUTHOR_ID1)).willReturn(Optional.of(existingAUTHOR1));
        given(authorRepository.findById(NOT_EXISTING_ID)).willReturn(Optional.empty());
        given(genreRepository.findById(EXISTING_GENRE_ID1)).willReturn(Optional.of(existingGENRE1));
        val expectedBookDto = new BookDto(newBook);

        val actualBookDto = bookService.addBook(newTitle, EXISTING_AUTHOR_ID1, EXISTING_GENRE_ID1, new ArrayList<>());
        assertThat(actualBookDto).usingRecursiveComparison().isEqualTo(expectedBookDto);
        assertThrows(ApplicationException.class, () -> bookService.addBook(newTitle, NOT_EXISTING_ID, EXISTING_GENRE_ID1, new ArrayList<>()));

    }

    @DisplayName("Изменяет книгу")
    @Test
    void shouldUpdateBook() {
        val newTitle = "Книга";
        given(bookRepository.findById(EXISTING_BOOK_ID1)).willReturn(Optional.of(existingBOOK1));
        given(bookRepository.save(new Book(EXISTING_BOOK_ID1, newTitle, existingAUTHOR2, existingGENRE2, new ArrayList<>())))
                .willReturn(new Book(EXISTING_BOOK_ID1, newTitle, existingAUTHOR2, existingGENRE2, new ArrayList<>()));
        given(authorRepository.findById(EXISTING_AUTHOR_ID2)).willReturn(Optional.of(existingAUTHOR2));
        given(authorRepository.findById(NOT_EXISTING_ID)).willReturn(Optional.empty());
        given(genreRepository.findById(EXISTING_GENRE_ID2)).willReturn(Optional.of(existingGENRE2));
        val expectedBookDto = new BookDto(new Book(EXISTING_BOOK_ID1, newTitle, existingAUTHOR2, existingGENRE2,
                new ArrayList<>()));

        val actualBookDto = bookService.updateBook(EXISTING_BOOK_ID1, newTitle, EXISTING_AUTHOR_ID2, EXISTING_GENRE_ID2,
                new ArrayList<>());
        assertThat(actualBookDto).usingRecursiveComparison().isEqualTo(expectedBookDto);
        assertThrows(ApplicationException.class, () -> bookService.updateBook(EXISTING_BOOK_ID1, newTitle, NOT_EXISTING_ID, EXISTING_GENRE_ID2,
                new ArrayList<>()));
    }

    @DisplayName("Удаляет книгу")
    @Test
    void shouldDeleteBook() {
        given(bookRepository.findById(EXISTING_BOOK_ID1)).willReturn(Optional.of(existingBOOK1));
        given(bookRepository.findById(NOT_EXISTING_ID)).willReturn(Optional.empty());
        val expectedBookDto = new BookDto(existingBOOK1);

        val actualBookDto = bookService.deleteBook(EXISTING_BOOK_ID1);
        assertThat(actualBookDto).usingRecursiveComparison().isEqualTo(expectedBookDto);
        assertThrows(ApplicationException.class, () -> bookService.deleteBook(NOT_EXISTING_ID));
    }

    @DisplayName("Возвращает книгу по Id")
    @Test
    void shouldGetBook() {
        given(bookRepository.findById(EXISTING_BOOK_ID1)).willReturn(Optional.of(existingBOOK1));
        given(bookRepository.findById(NOT_EXISTING_ID)).willReturn(Optional.empty());
        val expectedBookDto = new BookDto(existingBOOK1);

        val actualBookDto = bookService.deleteBook(EXISTING_BOOK_ID1);
        assertThat(actualBookDto).usingRecursiveComparison().isEqualTo(expectedBookDto);
        assertThrows(ApplicationException.class, () -> bookService.deleteBook(NOT_EXISTING_ID));
    }
}
