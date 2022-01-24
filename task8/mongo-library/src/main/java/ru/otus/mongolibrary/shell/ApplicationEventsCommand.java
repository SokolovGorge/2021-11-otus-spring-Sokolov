package ru.otus.mongolibrary.shell;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.mongolibrary.dto.BookDto;
import ru.otus.mongolibrary.dto.RemarkDto;
import ru.otus.mongolibrary.service.AuthorService;
import ru.otus.mongolibrary.service.BookService;
import ru.otus.mongolibrary.service.GenreService;
import ru.otus.mongolibrary.service.RemarkService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommand {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final RemarkService remarkService;

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
                             @ShellOption(help = "Id автора") String authorId,
                             @ShellOption(help = "Id жанра") String genreId) {
        BookDto book = bookService.addBook(title, authorId, genreId);
        return "Добавлена книга: " + book;
    }

    @ShellMethod(value = "Изменить книгу", key = {"updateBook", "ub"})
    public String updateBook(@ShellOption(help = "Id книги") String id,
                             @ShellOption(help = "Заголовок книги") String title,
                             @ShellOption(help = "Id автора") String authorId,
                             @ShellOption(help = "Id жанра") String genreId,
                             @ShellOption(help = "Список примечаний (разделитель |)", defaultValue = "") String remarks) {
        BookDto book = bookService.updateBook(id, title, authorId, genreId);
        return "Изменена книга: " + book;
    }

    @ShellMethod(value = "Удалить книгу", key = {"deleteBook", "db"})
    public String deleteBook(@ShellOption(help = "Id книги") String id) {
        BookDto book = bookService.deleteBook(id);
        return "Удалена книга: " + book;
    }

    @ShellMethod(value = "Считать книгу", key = {"getBook", "gb"})
    public String getBook(@ShellOption(help = "Id книги") String id) {
        return "Книга: " + bookService.getBook(id);
    }

    @ShellMethod(value = "Вывести список примечаний книги", key = {"publishRemarks", "pr"})
    public String publishRemarksByBook(@ShellOption(help = "Id книги") String bookId) {
        val sb = new StringBuilder("Список примечаний книги:\n");
        remarkService.getAllRemarksByBook(bookId).forEach(remark -> {
            sb.append(remark);
            sb.append("\n");
        });
        return sb.toString();
    }

    @ShellMethod(value = "Добавить примечание", key = {"addRemark", "ar"})
    public String insertRemark(@ShellOption(help = "Id книги") String bookId, @ShellOption(help = "Примечание") String text) {
        RemarkDto remarkDto = remarkService.addRemark(bookId, text);
        return "Добавлено примечание: " + remarkDto;
    }

}
