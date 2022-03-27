package ru.otus.weblibrary.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;
import ru.otus.weblibrary.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @PostFilter("hasPermission(filterObject, 'READ')")
    @EntityGraph("book-author-genre-entity-graph")
    List<Book> findAll();

    @PostFilter("hasPermission(filterObject, 'READ')")
    @EntityGraph("book-author-genre-entity-graph")
    List<Book> findByTitle(String title);

    @PostFilter("hasPermission(filterObject, 'READ')")
    @EntityGraph("book-author-genre-entity-graph")
    List<Book> findByAuthorSurName(String surname);

    @PostFilter("hasPermission(filterObject, 'READ')")
    @EntityGraph("book-author-genre-entity-graph")
    List<Book> findByGenreName(String name);
}
