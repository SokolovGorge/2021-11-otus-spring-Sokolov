package ru.otus.weblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.weblibrary.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
