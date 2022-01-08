package ru.otus.jdbclibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.jdbclibrary.dao.GenreDao;
import ru.otus.jdbclibrary.domain.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao dao;

    public GenreServiceImpl(GenreDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAllGenres() {
        return dao.getAll();
    }
}
