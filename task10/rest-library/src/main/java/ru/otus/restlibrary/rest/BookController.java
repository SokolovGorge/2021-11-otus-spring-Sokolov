package ru.otus.restlibrary.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.restlibrary.dto.BookDto;
import ru.otus.restlibrary.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/books")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/api/books/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id") long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @PostMapping("/api/books")
    public ResponseEntity<Long> newBook(@RequestParam("title") String title,
                                     @RequestParam("authorId") long authorId,
                                     @RequestParam("genreId") long genreId) {
        return ResponseEntity.ok(bookService.addBook(title, authorId, genreId).getId());
    }

    @PutMapping("/api/books")
    public ResponseEntity<?> saveBook(@RequestParam("id") long id,
                         @RequestParam("title") String title,
                         @RequestParam("authorId") long authorId,
                         @RequestParam("genreId") long genreId) {
        bookService.updateBook(id, title, authorId, genreId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

}
