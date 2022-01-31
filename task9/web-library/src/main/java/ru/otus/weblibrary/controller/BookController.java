package ru.otus.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.weblibrary.model.BookEditModel;
import ru.otus.weblibrary.service.AuthorService;
import ru.otus.weblibrary.service.BookService;
import ru.otus.weblibrary.service.GenreService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/booklist")
    public String listBooks(Model model) {
        val books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "booklist";
    }

    @GetMapping("/bookedit")
    public String editBook(@RequestParam("id") long id, Model model) {
        return prepareEditBook(id, model);
    }

    @GetMapping("/booknew")
    public String newBook(Model model) {
        return prepareEditBook(0, model);
    }

    @PostMapping("/bookedit")
    public String saveBook(@Valid @ModelAttribute("book") BookEditModel bookEditModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // восстанавливаем сборошенные атрибуты ???
            model.addAttribute("pageTitle", bookEditModel.getId() == 0 ? "Новая книга" : "Редактирование книги");
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("genres", genreService.getAllGenres());
            return "bookedit";
        }
        if (0 == bookEditModel.getId()) {
            bookService.addBook(bookEditModel.getTitle(), bookEditModel.getAuthorId(), bookEditModel.getGenreId());
        } else {
            bookService.updateBook(bookEditModel.getId(), bookEditModel.getTitle(), bookEditModel.getAuthorId(), bookEditModel.getGenreId());
        }
        return "redirect:/booklist";
    }

    @GetMapping("bookdelete")
    public String confirmDeleteBook(@RequestParam("id") long id, Model model) {
        val book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "bookdelete";
    }

    @PostMapping("bookdelete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteBook(id);
        return "redirect:/booklist";
    }

    private String prepareEditBook(long id, Model model) {
        BookEditModel bookEditModel;
        if (id != 0) {
            bookEditModel = new BookEditModel(bookService.getBook(id));
        } else {
            bookEditModel = new BookEditModel();
        }
        model.addAttribute("pageTitle", id == 0 ? "Новая книга" : "Редактирование книги");
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("genres", genreService.getAllGenres());
        model.addAttribute("book", bookEditModel);
        return "bookedit";
    }

}
