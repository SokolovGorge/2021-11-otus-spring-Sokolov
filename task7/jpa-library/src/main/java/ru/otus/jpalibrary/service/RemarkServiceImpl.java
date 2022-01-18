package ru.otus.jpalibrary.service;

import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.jpalibrary.domain.Remark;
import ru.otus.jpalibrary.dto.RemarkDto;
import ru.otus.jpalibrary.exceptions.ApplicationException;
import ru.otus.jpalibrary.repository.BookRepository;
import ru.otus.jpalibrary.repository.RemarkRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class RemarkServiceImpl implements RemarkService {

    private final BookRepository bookRepository;
    private final RemarkRepository remarkRepository;

    public RemarkServiceImpl(BookRepository bookRepository, RemarkRepository remarkRepository) {
        this.bookRepository = bookRepository;
        this.remarkRepository = remarkRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<RemarkDto> getAllRemarksByBook(long bookId) {
        return remarkRepository.findAllRemarksByBookId(bookId).stream().map(RemarkDto::new).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RemarkDto addRemark(long bookId, String text) {
        val book = bookRepository.findById(bookId).orElseThrow(() -> new ApplicationException("Не найдена книга Id = " + bookId));
        val remark = new Remark(null, book, text);
        return new RemarkDto(remarkRepository.save(remark));
    }
}
