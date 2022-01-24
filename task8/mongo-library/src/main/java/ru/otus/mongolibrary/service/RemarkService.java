package ru.otus.mongolibrary.service;

import ru.otus.mongolibrary.dto.RemarkDto;

import java.util.List;

public interface RemarkService {

    List<RemarkDto> getAllRemarksByBook(String bookId);

    RemarkDto addRemark(String bookId, String text);

}
