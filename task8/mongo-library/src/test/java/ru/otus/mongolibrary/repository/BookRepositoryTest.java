package ru.otus.mongolibrary.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("Репозиторий на основе Data MongoDB для работы с книгами должен")
@DataMongoTest
@ComponentScan({ "ru.otus.example.mongolibrary.repository"})
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
    void shouldReturnExpectedBookList() {
        val books = bookRepository.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("Возвращать книги по заголовку")
    @Test
    void shouldReturnBookByTitle() {
        val books = bookRepository.findByTitle(EXISTING_TITLE);
        assertThat(books).isNotNull().isNotEmpty();
    }

    @DisplayName("Возвращать книги по фамилии автора")
    @Test
    void shouldReturnBookByAuthorSurName() {
        val books = bookRepository.findByAuthorSurName(EXISTING_AUTHOR_SURNAME);
        assertThat(books).isNotNull().isNotEmpty();
    }

    @DisplayName("Возвращать книги по названию жанра")
    @Test
    void shouldReturnBookByGenreName() {
        val books = bookRepository.findByGenreName(EXISTING_GENRE_NAME);
        assertThat(books).isNotNull().isNotEmpty();
    }

    @DisplayName("Рекурсивно изменять книгу")
    @Test
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
