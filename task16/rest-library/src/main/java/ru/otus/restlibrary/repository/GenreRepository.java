package ru.otus.restlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.restlibrary.domain.Genre;

@RepositoryRestResource(path = "genres")
public interface GenreRepository extends JpaRepository<Genre, Long> {

}
