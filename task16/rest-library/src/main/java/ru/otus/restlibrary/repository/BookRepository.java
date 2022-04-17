package ru.otus.restlibrary.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.restlibrary.domain.Book;

import java.util.List;

@RepositoryRestResource(path = "books")
public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph("book-author-genre-entity-graph")
    List<Book> findAll();

    @EntityGraph("book-author-genre-entity-graph")
    List<Book> findByTitle(String title);

    @EntityGraph("book-author-genre-entity-graph")
    List<Book> findByAuthorSurName(String surname);

    @EntityGraph("book-author-genre-entity-graph")
    List<Book> findByGenreName(String name);
}
