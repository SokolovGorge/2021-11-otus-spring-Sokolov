package ru.otus.restlibrary.service;

import ru.otus.restlibrary.dto.RemarkDto;

import java.util.List;

public interface RemarkService {

    List<RemarkDto> getAllRemarksByBook(long bookId);

    RemarkDto addRemark(long bookId, String text);
}
