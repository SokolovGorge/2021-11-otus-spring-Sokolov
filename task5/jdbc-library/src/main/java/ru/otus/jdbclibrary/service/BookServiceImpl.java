package ru.otus.jdbclibrary.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.jdbclibrary.dao.AuthorDao;
import ru.otus.jdbclibrary.dao.BookDao;
import ru.otus.jdbclibrary.dao.GenreDao;
import ru.otus.jdbclibrary.domain.Author;
import ru.otus.jdbclibrary.domain.Book;
import ru.otus.jdbclibrary.domain.Genre;
import ru.otus.jdbclibrary.exceptions.ApplicationException;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookDao bookDao;

    public BookServiceImpl(AuthorDao authorDao, GenreDao genreDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.bookDao = bookDao;
    }


    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    @Transactional
    @Override
    public Book addBook(String title, long authorId, long genreId) {
        Book book = makeBook(null, title, authorId, genreId);
        book.setId(bookDao.insert(book));
        return book;
    }

    @Transactional
    @Override
    public Book updateBook(long id, String title, long authorId, long genreId) {
        Book book = makeBook(id, title, authorId, genreId);
        bookDao.update(book);
        return book;
    }

    @Transactional
    @Override
    public Book deleteBook(long id) {
        try {
            Book book = bookDao.getById(id);
            bookDao.deleteById(id);
            return book;
        } catch (EmptyResultDataAccessException ex) {
            throw new ApplicationException("Не найдена книга с Id = " + id);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Book getBook(long id) {
        try {
            return bookDao.getById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ApplicationException("Не найдена книга с Id = " + id);
        }
    }

    private Book makeBook(Long id, String title, long authorId, long genreId) {
        Author author;
        Genre genre;
        try {
            author = authorDao.getById(authorId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ApplicationException("Не найден австор с Id = " + authorId);
        }
        try {
            genre = genreDao.getById(genreId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ApplicationException("Не найден жанр с Id = " + genreId);
        }
        return new Book(id, title, author, genre);
    }
}
