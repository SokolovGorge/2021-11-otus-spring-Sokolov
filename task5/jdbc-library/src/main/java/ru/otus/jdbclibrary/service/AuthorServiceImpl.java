package ru.otus.jdbclibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.jdbclibrary.dao.AuthorDao;
import ru.otus.jdbclibrary.domain.Author;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao dao;

    public AuthorServiceImpl(AuthorDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAllAuthors() {
        return dao.getAll();
    }
}
