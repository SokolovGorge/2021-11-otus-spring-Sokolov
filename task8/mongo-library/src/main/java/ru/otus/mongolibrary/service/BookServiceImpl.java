package ru.otus.mongolibrary.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.otus.mongolibrary.domain.Book;
import ru.otus.mongolibrary.dto.BookDto;
import ru.otus.mongolibrary.exceptions.ApplicationException;
import ru.otus.mongolibrary.repository.AuthorRepository;
import ru.otus.mongolibrary.repository.BookRepository;
import ru.otus.mongolibrary.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(BookDto::new).collect(Collectors.toList());
    }

    @Override
    public BookDto addBook(String title, String authorId, String genreId) {
        val author = authorRepository.findById(authorId).orElseThrow(() -> new ApplicationException("Не найден австор с Id = " + authorId));
        val genre = genreRepository.findById(genreId).orElseThrow(() -> new ApplicationException("Не найден жанр с Id = " + genreId));
        val book = new Book(title, author, genre);
        return new BookDto(bookRepository.save(book));
    }

    @Override
    public BookDto updateBook(String id, String title, String authorId, String genreId) {
        val book = bookRepository.findById(id).orElseThrow(() -> new ApplicationException("Не найдена книга Id = " + id));
        val author = authorRepository.findById(authorId).orElseThrow(() -> new ApplicationException("Не найден австор с Id = " + authorId));
        val genre = genreRepository.findById(genreId).orElseThrow(() -> new ApplicationException("Не найден жанр с Id = " + genreId));
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        return new BookDto(bookRepository.saveWithRecursive(book));
    }

    @Override
    public BookDto deleteBook(String id) {
        val book = bookRepository.findById(id).orElseThrow(() -> new ApplicationException("Не найдена книга Id = " + id));
        bookRepository.deleteByIdWithRecursive(id);
        return new BookDto(book);
    }

    @Override
    public BookDto getBook(String id) {
        val book = bookRepository.findById(id).orElseThrow(() -> new ApplicationException("Не найдена книга Id = " + id));
        return new BookDto(book);
    }
}
