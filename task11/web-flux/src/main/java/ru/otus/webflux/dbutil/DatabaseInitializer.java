package ru.otus.webflux.dbutil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.webflux.domain.Author;
import ru.otus.webflux.domain.Book;
import ru.otus.webflux.domain.Genre;
import ru.otus.webflux.repository.AuthorRepository;
import ru.otus.webflux.repository.BookRepository;
import ru.otus.webflux.repository.GenreRepository;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    public void initData() {
        authorRepository.save(new Author("Агата", "Кристи")).subscribe(author ->
                genreRepository.save(new Genre("Детектив")).subscribe(genre ->
                        bookRepository.save(new Book("Восточный экспресс", author, genre)).subscribe()
                ));

        authorRepository.save(new Author("Сергей", "Лукьяненко")).subscribe(author ->
                genreRepository.save(new Genre("Фантастика")).subscribe(genre ->
                        bookRepository.save(new Book("Императоры иллюзий", author, genre)).subscribe()
                ));

        authorRepository.save(new Author("Артур", "Конан Дойл")).subscribe(author ->
                genreRepository.save(new Genre("Приключения")).subscribe(genre ->
                        bookRepository.save(new Book("Вокруг света за 80 дней", author, genre)).subscribe()
                ));

        authorRepository.save(new Author("Лев", "Толстой")).subscribe(author ->
                genreRepository.save(new Genre("Роман")).subscribe(genre ->
                        bookRepository.save(new Book("Война и мир", author, genre)).subscribe()
                ));

        authorRepository.save(new Author("Сергей", "Капица")).subscribe(author ->
                genreRepository.save(new Genre("Научная книга")).subscribe(genre ->
                        bookRepository.save(new Book("Парадоксы роста", author, genre)).subscribe()
                ));
    }

}
