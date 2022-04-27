package ru.otus.restlibrary.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.restlibrary.domain.Remark;
import ru.otus.restlibrary.dto.RemarkDto;
import ru.otus.restlibrary.exceptions.ApplicationException;
import ru.otus.restlibrary.repository.BookRepository;
import ru.otus.restlibrary.repository.RemarkRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RemarkServiceImpl implements RemarkService {

    private final BookRepository bookRepository;
    private final RemarkRepository remarkRepository;

    @HystrixCommand(commandKey = "getRemarkKey", fallbackMethod = "getDummyList")
    @Transactional(readOnly = true)
    @Override
    public List<RemarkDto> getAllRemarksByBook(long bookId) {
        return remarkRepository.findAllRemarksByBookId(bookId).stream().map(RemarkDto::new).collect(Collectors.toList());
    }

    @HystrixCommand(commandKey = "updBookKey", fallbackMethod = "getDummy")
    @Transactional
    @Override
    public RemarkDto addRemark(long bookId, String text) {
        val book = bookRepository.findById(bookId).orElseThrow(() -> new ApplicationException("Не найдена книга Id = " + bookId));
        val remark = new Remark(null, book, text);
        return new RemarkDto(remarkRepository.save(remark));
    }

    public List<RemarkDto> getDummyList() {
        return Collections.singletonList(RemarkDto.DUMMY);
    }

    public RemarkDto getDummy() {
        return RemarkDto.DUMMY;
    }
}
