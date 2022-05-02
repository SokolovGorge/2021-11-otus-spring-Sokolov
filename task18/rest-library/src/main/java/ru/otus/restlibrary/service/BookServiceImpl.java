package ru.otus.restlibrary.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.restlibrary.domain.Book;
import ru.otus.restlibrary.dto.BookDto;
import ru.otus.restlibrary.exceptions.ApplicationException;
import ru.otus.restlibrary.repository.AuthorRepository;
import ru.otus.restlibrary.repository.BookRepository;
import ru.otus.restlibrary.repository.GenreRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @HystrixCommand(fallbackMethod = "getDummyList")
    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getAllBooks() {

        if (RandomUtils.nextInt(1, 10) < 5) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return bookRepository.findAll().stream().map(BookDto::new).collect(Collectors.toList());
    }

    @HystrixCommand(commandKey = "getBookKey", fallbackMethod = "getDummy")
    @Transactional
    @Override
    public BookDto addBook(String title, long authorId, long genreId) {
        val author = authorRepository.findById(authorId).orElseThrow(() -> new ApplicationException("Не найден австор с Id = " + authorId));
        val genre = genreRepository.findById(genreId).orElseThrow(() -> new ApplicationException("Не найден жанр с Id = " + genreId));
        val book = new Book(null, title, author, genre);
        return  new BookDto(bookRepository.save(book));
    }

    @HystrixCommand(commandKey = "updBookKey", fallbackMethod = "getDummy")
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

    @HystrixCommand(commandKey = "updBookKey", fallbackMethod = "getDummy")
    @Transactional
    @Override
    public BookDto deleteBook(long id) {
        val book = bookRepository.findById(id).orElseThrow(() -> new ApplicationException("Не найдена книга с Id = " + id));
        bookRepository.deleteById(id);
        return new BookDto(book);
    }

    @HystrixCommand(commandKey = "getBookKey", fallbackMethod = "getDummy")
    @Transactional(readOnly = true)
    @Override
    public BookDto getBook(long id) {
        val book = bookRepository.findById(id).orElseThrow(() -> new ApplicationException("Не найдена книга с Id = " + id));
        return new BookDto(book);
    }

    public List<BookDto> getDummyList() {
        return Collections.singletonList(BookDto.DUMMY);
    }

    public BookDto getDummy() {
        return BookDto.DUMMY;
    }
}
