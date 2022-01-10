package ru.otus.ormlibrary.service;

import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.ormlibrary.dto.BookDto;
import ru.otus.ormlibrary.exceptions.ApplicationException;
import ru.otus.ormlibrary.models.Book;
import ru.otus.ormlibrary.models.Remark;
import ru.otus.ormlibrary.repositories.AuthorRepository;
import ru.otus.ormlibrary.repositories.BookRepository;
import ru.otus.ormlibrary.repositories.GenreRepository;

import java.util.ArrayList;
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
    public BookDto addBook(String title, long authorId, long genreId, List<String> remarks) {
        val optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isEmpty()) {
            throw new ApplicationException("Не найден австор с Id = " + authorId);
        }
        val optionalGenre = genreRepository.findById(genreId);
        if (optionalGenre.isEmpty()) {
            throw new ApplicationException("Не найден жанр с Id = " + genreId);
        }
        val book = new Book(null, title, optionalAuthor.get(), optionalGenre.get(), null);
        if (remarks != null) {
            val remarkList = new ArrayList<Remark>();
            remarks.forEach(r -> remarkList.add(new Remark(null, book, r)));
            book.setRemarks(remarkList);
        }
        return new BookDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public BookDto updateBook(long id, String title, long authorId, long genreId, List<String> remarks) {
        val optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new ApplicationException("Не найдена книга Id = " + id);
        }
        Book book = optionalBook.get();
        book.setTitle(title);
        book.getRemarks().clear();
 /*       val optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isEmpty()) {
            throw new ApplicationException("Не найден австор с Id = " + authorId);
        }
        book.setAuthor(optionalAuthor.get());
        val optionalGenre = genreRepository.findById(genreId);
        if (optionalGenre.isEmpty()) {
            throw new ApplicationException("Не найден жанр с Id = " + genreId);
        }
        book.setGenre(optionalGenre.get());


        if (null == remarks || remarks.isEmpty()) {
            book.getRemarks().clear();
        } else {
            val remarkList = new ArrayList<Remark>();
            if (null == book.getRemarks() || book.getRemarks().isEmpty()) {
                remarkList.addAll(remarks.stream().map(st -> new Remark(null, book, st)).collect(Collectors.toList()));
            } else {
                remarkList.addAll(book.getRemarks().stream().filter(r -> remarks.contains(r.getText())).collect(Collectors.toList()));
                val existsTexts = book.getRemarks().stream().map(Remark::getText).collect(Collectors.toList());
                remarkList.addAll(remarks.stream().filter(st -> !existsTexts.contains(st)).map(st -> new Remark(null, book, st)).collect(Collectors.toList()));
            }
            book.setRemarks(remarkList);
        }*/
        bookRepository.save(book);
        return null;
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
