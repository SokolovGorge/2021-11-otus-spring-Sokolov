package ru.otus.mongolibrary.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.mongolibrary.domain.Author;
import ru.otus.mongolibrary.domain.Book;
import ru.otus.mongolibrary.domain.Genre;
import ru.otus.mongolibrary.domain.Remark;
import ru.otus.mongolibrary.repository.AuthorRepository;
import ru.otus.mongolibrary.repository.BookRepository;
import ru.otus.mongolibrary.repository.GenreRepository;
import ru.otus.mongolibrary.repository.RemarkRepository;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author author1;
    private Author author2;
    private Author author3;
    private Author author4;
    private Author author5;

    private Genre genre1;
    private Genre genre2;
    private Genre genre3;
    private Genre genre4;
    private Genre genre5;

    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;
    private Book book5;

    @ChangeSet(order = "000", id = "dropDB", author = "sgv", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "sgv", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        author1 = repository.save(new Author("Агата", "Кристи"));
        author2 = repository.save(new Author("Сергей", "Лукьяненко"));
        author3 = repository.save(new Author("Артур", "Конан Дойл"));
        author4 = repository.save(new Author("Лев", "Толстой"));
        author5 = repository.save(new Author("Сергей", "Капица"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "sgv", runAlways = true)
    public void initGenres(GenreRepository repository) {
        genre1 = repository.save(new Genre("Детектив"));
        genre2 = repository.save(new Genre("Фантастика"));
        genre3 = repository.save(new Genre("Приключения"));
        genre4 = repository.save(new Genre("Роман"));
        genre5 = repository.save(new Genre("Научная книга"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "sgv", runAlways = true)
    public void initBooks (BookRepository repository) {
        book1 = repository.save(new Book("Восточный экспресс", author1, genre1));
        book2 = repository.save(new Book("Императоры иллюзий", author2, genre2));
        book3 = repository.save(new Book("Вокруг света за 80 дней", author3, genre3));
        book4 = repository.save(new Book("Война и мир", author4, genre4));
        book5 = repository.save(new Book("Парадоксы роста", author5, genre5));
    }

    @ChangeSet(order = "004", id = "initRemarks", author = "sgv", runAlways = true)
    public void initRemarks(RemarkRepository repository) {
        initRemarksByBook(repository, book1, "1");
        initRemarksByBook(repository, book2, "2");
        initRemarksByBook(repository, book3, "3");
        initRemarksByBook(repository, book4, "4");
        initRemarksByBook(repository, book5, "5");
    }

    private void initRemarksByBook(RemarkRepository repository, Book book, String index) {
        for (int i = 1; i <= 4; i++) {
            repository.save(new Remark(book, "Примечание" + index + i));
        }
    }
}
