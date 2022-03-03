package ru.otus.weblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.weblibrary.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
