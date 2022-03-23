package ru.otus.weblibrary.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.weblibrary.domain.Book;
import ru.otus.weblibrary.dto.BookDto;
import ru.otus.weblibrary.exceptions.ApplicationException;
import ru.otus.weblibrary.repository.AuthorRepository;
import ru.otus.weblibrary.repository.BookRepository;
import ru.otus.weblibrary.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(BookDto::new).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BookDto addBook(String title, long authorId, long genreId) {
        val author = authorRepository.findById(authorId).orElseThrow(() -> new ApplicationException("Не найден австор с Id = " + authorId));
        val genre = genreRepository.findById(genreId).orElseThrow(() -> new ApplicationException("Не найден жанр с Id = " + genreId));
        val book = new Book(null, title, author, genre);
        val newBook = bookRepository.save(book);
        return  new BookDto(newBook);
    }

    @Transactional
    @Override
    public BookDto updateBook(long id, String title, long authorId, long genreId) {
        val book = bookRepository.findById(id).orElseThrow(() -> new ApplicationException("Не найдена книга Id = " + id));
        book.setTitle(title);
        val author = authorRepository.findById(authorId).orElseThrow(() -> new ApplicationException("Не найден австор с Id = " + authorId));
        book.setAuthor(author);
        val genre = genreRepository.findById(genreId).orElseThrow(() -> new ApplicationException("Не найден жанр с Id = " + genreId));
        book.setGenre(genre);
        return new BookDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public BookDto deleteBook(long id) {
        val book = bookRepository.findById(id).orElseThrow(() -> new ApplicationException("Не найдена книга с Id = " + id));
        bookRepository.deleteById(id);
        return new BookDto(book);
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto getBook(long id) {
        val book = bookRepository.findById(id).orElseThrow(() -> new ApplicationException("Не найдена книга с Id = " + id));
        return new BookDto(book);
    }
}
