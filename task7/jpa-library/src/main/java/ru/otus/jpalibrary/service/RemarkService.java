package ru.otus.jpalibrary.service;


import ru.otus.jpalibrary.dto.RemarkDto;

import java.util.List;

public interface RemarkService {

    List<RemarkDto> getAllRemarksByBook(long bookId);

    RemarkDto addRemark(long bookId, String text);
}
