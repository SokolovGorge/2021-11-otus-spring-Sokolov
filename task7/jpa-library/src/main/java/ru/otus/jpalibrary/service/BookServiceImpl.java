package ru.otus.jpalibrary.service;

import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.jpalibrary.domain.Book;
import ru.otus.jpalibrary.dto.BookDto;
import ru.otus.jpalibrary.exceptions.ApplicationException;
import ru.otus.jpalibrary.repository.AuthorRepository;
import ru.otus.jpalibrary.repository.BookRepository;
import ru.otus.jpalibrary.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(BookDto::new).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BookDto addBook(String title, long authorId, long genreId) {
        val optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isEmpty()) {
            throw new ApplicationException("Не найден австор с Id = " + authorId);
        }
        val optionalGenre = genreRepository.findById(genreId);
        if (optionalGenre.isEmpty()) {
            throw new ApplicationException("Не найден жанр с Id = " + genreId);
        }
        val book = new Book(null, title, optionalAuthor.get(), optionalGenre.get());
        return new BookDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public BookDto updateBook(long id, String title, long authorId, long genreId) {
        val optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new ApplicationException("Не найдена книга Id = " + id);
        }
        Book book = optionalBook.get();
        book.setTitle(title);

        val optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isEmpty()) {
            throw new ApplicationException("Не найден австор с Id = " + authorId);
        }
        book.setAuthor(optionalAuthor.get());
        val optionalGenre = genreRepository.findById(genreId);
        if (optionalGenre.isEmpty()) {
            throw new ApplicationException("Не найден жанр с Id = " + genreId);
        }
        book.setGenre(optionalGenre.get());
        return new BookDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public BookDto deleteBook(long id) {
        val optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new ApplicationException("Не найдена книга с Id = " + id);
        }
        bookRepository.deleteById(id);
        return new BookDto(optionalBook.get());
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto getBook(long id) {
        val optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new ApplicationException("Не найдена книга с Id = " + id);
        }
        return new BookDto(optionalBook.get());
    }
}
