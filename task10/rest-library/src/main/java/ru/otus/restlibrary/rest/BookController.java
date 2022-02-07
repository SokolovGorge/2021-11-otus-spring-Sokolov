package ru.otus.restlibrary.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BookDto> getBook(@RequestParam("id") long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @PostMapping("/api/savebook")
    public ResponseEntity saveBook(@RequestParam("id") long id,
                         @RequestParam("title") String title,
                         @RequestParam("authorId") long authorId,
                         @RequestParam("genreId") long genreId) {
        bookService.updateBook(id, title, authorId, genreId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/api/deletebook")
    public ResponseEntity deleteBook(@RequestParam("id") long id) {
        bookService.deleteBook(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
