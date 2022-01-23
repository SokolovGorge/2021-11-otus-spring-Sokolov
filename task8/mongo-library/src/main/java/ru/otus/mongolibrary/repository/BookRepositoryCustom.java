package ru.otus.mongolibrary.repository;

import ru.otus.mongolibrary.domain.Book;

public interface BookRepositoryCustom {

    void deleteByIdWithRecursive(String id);

    Book saveWithRecursive(Book book);
}
