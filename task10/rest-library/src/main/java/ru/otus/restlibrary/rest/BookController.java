package ru.otus.restlibrary.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.restlibrary.dto.BookDto;
import ru.otus.restlibrary.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/getbooks")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/api/getbook")
    public BookDto getBook(@RequestParam("id") long id) {
        return bookService.getBook(id);
    }

    @PostMapping("/api/savebook")
    public void saveBook(@RequestParam("id") long id,
                         @RequestParam("title") String title,
                         @RequestParam("authorId") long authorId,
                         @RequestParam("genreId") long genreId) {
        bookService.updateBook(id, title, authorId, genreId);
    }

    @PostMapping("/api/deletebook")
    public void deleteBook(@RequestParam("id") long id) {
        bookService.deleteBook(id);
    }

}
