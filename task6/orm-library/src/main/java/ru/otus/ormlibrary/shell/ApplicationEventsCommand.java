package ru.otus.ormlibrary.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ApplicationEventsCommand {


    @ShellMethod(value = "Вывести список авторов", key = {"publishAuthors", "pa"})
    public String publishAuthors() {
        return null;
    }

    @ShellMethod(value = "Вывести список жанров", key = {"publishGenres", "pg"})
    public String publishGenres() {
        return null;
    }

    @ShellMethod(value = "Вывести список книг", key = {"publishBooks", "pb"})
    public String publishBooks() {
        return null;
    }

    @ShellMethod(value = "Добавить книгу", key = {"addBook", "ab"})
    public String insertBook(@ShellOption(help = "Заголовок книги") String title,
                             @ShellOption(help = "Id автора") long authorId,
                             @ShellOption(help = "Id жанра") long genreId) {
        return "Добавлена книга: ";
    }

    @ShellMethod(value = "Изменить книгу", key = {"updateBook", "ub"})
    public String updateBook(@ShellOption(help = "Id книги") long id,
                             @ShellOption(help = "Заголовок книги") String title,
                             @ShellOption(help = "Id автора") long authorId,
                             @ShellOption(help = "Id жанра") long genreId) {
         return "Изменена книга: ";
    }

    @ShellMethod(value = "Удалить книгу", key = {"deleteBook", "db"})
    public String deleteBook(@ShellOption(help = "Id книги") long id) {
        return "Удалена книга: ";
    }

    @ShellMethod(value = "Считать книгу", key = {"getBook", "gb"})
    public String getBook(@ShellOption(help = "Id книги") long id) {
         return "Книга: ";
    }

}
