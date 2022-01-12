package ru.otus.ormlibrary.repositories;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.ormlibrary.models.Author;
import ru.otus.ormlibrary.models.Book;
import ru.otus.ormlibrary.models.Genre;
import ru.otus.ormlibrary.models.Remark;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами должен")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    private static final int EXPECTED_BOOK_COUNT = 2;
    private static final int EXPECTED_REMARK_COUNT = 4;
    private static final long EXPECTED_QUERIES_COUNT = 2;
    private static final long EXISTING_BOOK_ID = 1;
    private static final long EXISTING_GENRE_ID1 = 1;
    private static final long EXISTING_AUTHOR_ID1 = 1;

    @Autowired
    private BookRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Возвращает ожидаемое кол-во книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        val actualAuthorsCount = repositoryJpa.count();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("Сохранить книгу в БД")
    @Test
    void shouldSaveBook() {

        val existingAuthor = em.find(Author.class, EXISTING_AUTHOR_ID1);
        val existingGenre = em.find(Genre.class, EXISTING_GENRE_ID1);
        val newBook = new Book(null, "Title", existingAuthor, existingGenre, null);
        val newRemarks = new ArrayList<Remark>();
        newRemarks.add(new Remark(null, newBook, "Remark1"));
        newRemarks.add(new Remark(null, newBook, "Remark2"));
        newBook.setRemarks(newRemarks);

        var expectedBook = repositoryJpa.save(newBook);
        var actualBook = repositoryJpa.findById(expectedBook.getId()).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);

        val updatingBook = repositoryJpa.findById(EXISTING_BOOK_ID).get();
        updatingBook.setTitle("Test");
        updatingBook.getRemarks().clear();
        expectedBook = repositoryJpa.save(updatingBook);
        em.flush();
        actualBook = repositoryJpa.findById(EXISTING_BOOK_ID).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Удалить заданную книгу по id")
    @Test
    void shouldCorrectDeleteBookById() {
        val optionalExistingBook = repositoryJpa.findById(EXISTING_BOOK_ID);
        assertThat(optionalExistingBook.isPresent());
        repositoryJpa.deleteById(EXISTING_BOOK_ID);
        val optionalDeletingBook = repositoryJpa.findById(EXISTING_BOOK_ID);
        assertThat(optionalDeletingBook.isEmpty());
    }

    @DisplayName("Возвращать книгу по id")
    @Test
    void shouldFindExpectedBookById() {
        val optionalActualBook = repositoryJpa.findById(EXISTING_BOOK_ID);
        val expectedBook = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
        assertThat(optionalActualBook.get().getRemarks()).isNotNull().hasSize(EXPECTED_REMARK_COUNT);
    }

    @DisplayName("Возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val books = repositoryJpa.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_BOOK_COUNT);
        books.forEach(b -> assertThat(b.getRemarks()).isNotNull().hasSize(EXPECTED_REMARK_COUNT));

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);

    }

}
