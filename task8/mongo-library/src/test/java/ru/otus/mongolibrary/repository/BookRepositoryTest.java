package ru.otus.mongolibrary.repository;

import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Data MongoDB для работы с книгами должен")
@DataMongoTest
@ComponentScan({ "ru.otus.example.mongolibrary.repository"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookRepositoryTest {

    private static final int EXPECTED_BOOK_COUNT = 5;
    private static final String EXISTING_TITLE = "Императоры иллюзий";
    private static final String EXISTING_AUTHOR_SURNAME = "Кристи";
    private static final String EXISTING_GENRE_NAME = "Детектив";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RemarkRepository remarkRepository;

    @DisplayName("Возвращать ожидаемый список книг")
    @Test
    @Order(1)
    void shouldReturnExpectedBookList() {
        val books = bookRepository.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("Возвращать книги по заголовку")
    @Test
    @Order(2)
    void shouldReturnBookByTitle() {
        val books = bookRepository.findByTitle(EXISTING_TITLE);
        assertThat(books).isNotNull().isNotEmpty();
    }

    @DisplayName("Возвращать книги по фамилии автора")
    @Test
    @Order(3)
    void shouldReturnBookByAuthorSurName() {
        val books = bookRepository.findByAuthorSurName(EXISTING_AUTHOR_SURNAME);
        assertThat(books).isNotNull().isNotEmpty();
    }

    @DisplayName("Возвращать книги по названию жанра")
    @Test
    @Order(4)
    void shouldReturnBookByGenreName() {
        val books = bookRepository.findByGenreName(EXISTING_GENRE_NAME);
        assertThat(books).isNotNull().isNotEmpty();
    }

    @DisplayName("Рекурсивно изменять книгу")
    @Test
    @Order(5)
    void shouldUpdateBookWithRecursive() {
        val newTitle = "Title";
        val existingBook = bookRepository.findAll().get(0);
        existingBook.setTitle(newTitle);
        val savedBook = bookRepository.saveWithRecursive(existingBook);
        remarkRepository.findAllRemarksByBook(savedBook).forEach(remark -> {
            assertThat(remark.getBook()).usingRecursiveComparison().isEqualTo(savedBook);
        });

    }

    @DisplayName("Удалять книгу вместе с комментариями")
    @Test
    @Order(6)
    void shouldDeleteBookWithRecursive() {
        val existingBook = bookRepository.findAll().get(1);
        var remarks = remarkRepository.findAllRemarksByBook(existingBook);
        assertThat(remarks).isNotNull().isNotEmpty();
        bookRepository.deleteByIdWithRecursive(existingBook.getId());
        remarks = remarkRepository.findAllRemarksByBook(existingBook);
        assertThat(remarks).isNotNull().isEmpty();
        val isExistBook = remarkRepository.existsById(existingBook.getId());
        assertThat(isExistBook).isFalse();
    }

}
