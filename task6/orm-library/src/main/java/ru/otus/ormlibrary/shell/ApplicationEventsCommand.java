package ru.otus.ormlibrary.shell;

import lombok.val;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.ormlibrary.dto.BookDto;
import ru.otus.ormlibrary.dto.RemarkDto;
import ru.otus.ormlibrary.service.AuthorService;
import ru.otus.ormlibrary.service.BookService;
import ru.otus.ormlibrary.service.GenreService;
import ru.otus.ormlibrary.service.RemarkService;

@ShellComponent
public class ApplicationEventsCommand {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final RemarkService remarkService;

    public ApplicationEventsCommand(AuthorService authorService, GenreService genreService, BookService bookService, RemarkService remarkService) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookService = bookService;
        this.remarkService = remarkService;
    }

    @ShellMethod(value = "Вывести список авторов", key = {"publishAuthors", "pa"})
    public String publishAuthors() {
        val sb = new StringBuilder("Список авторов:\n");
        authorService.getAllAuthors().forEach(author -> {
            sb.append(author);
            sb.append("\n");
        });
        return sb.toString();
    }

    @ShellMethod(value = "Вывести список жанров", key = {"publishGenres", "pg"})
    public String publishGenres() {
        val sb = new StringBuilder("Список жанров:\n");
        genreService.getAllGenres().forEach(genre -> {
            sb.append(genre);
            sb.append("\n");
        });
        return sb.toString();
    }

    @ShellMethod(value = "Вывести список книг", key = {"publishBooks", "pb"})
    public String publishBooks() {
        val sb = new StringBuilder("Список книг:\n");
        bookService.getAllBooks().forEach(book -> {
            sb.append(book);
            sb.append("\n");
        });
        return sb.toString();
    }

    @ShellMethod(value = "Добавить книгу", key = {"addBook", "ab"})
    public String insertBook(@ShellOption(help = "Заголовок книги") String title,
                             @ShellOption(help = "Id автора") long authorId,
                             @ShellOption(help = "Id жанра") long genreId) {
        BookDto book = bookService.addBook(title, authorId, genreId);
        return "Добавлена книга: " + book;
    }

    @ShellMethod(value = "Изменить книгу", key = {"updateBook", "ub"})
    public String updateBook(@ShellOption(help = "Id книги") long id,
                             @ShellOption(help = "Заголовок книги") String title,
                             @ShellOption(help = "Id автора") long authorId,
                             @ShellOption(help = "Id жанра") long genreId,
                             @ShellOption(help = "Список примечаний (разделитель |)", defaultValue = "") String remarks) {
        BookDto book = bookService.updateBook(id, title, authorId, genreId);
         return "Изменена книга: " + book;
    }

    @ShellMethod(value = "Удалить книгу", key = {"deleteBook", "db"})
    public String deleteBook(@ShellOption(help = "Id книги") long id) {
        BookDto book = bookService.deleteBook(id);
        return "Удалена книга: " + book;
    }

    @ShellMethod(value = "Считать книгу", key = {"getBook", "gb"})
    public String getBook(@ShellOption(help = "Id книги") long id) {
         return "Книга: " + bookService.getBook(id);
    }

    @ShellMethod(value = "Вывести список примечаний книги", key = {"publishRemarks", "pr"})
    public String publishRemarksByBook(@ShellOption(help = "Id книги") long bookId) {
        val sb = new StringBuilder("Список примечаний книги:\n");
        remarkService.getAllRemarksByBook(bookId).forEach(remark -> {
            sb.append(remark);
            sb.append("\n");
        });
        return sb.toString();
    }

    @ShellMethod(value = "Добавить примечание", key = {"addRemark", "ar"})
    public String insertRemark(@ShellOption(help = "Id книги") long bookId, @ShellOption(help = "Примечание") String text) {
        RemarkDto remarkDto = remarkService.addRemark(bookId, text);
        return "Добавлено примечание: " + remarkDto;
    }

}
