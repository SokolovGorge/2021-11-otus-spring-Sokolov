package ru.otus.hhexplorer.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestItem {

    private String profCode;
    private String areaCode;
    private String words;
    private Integer page;
}
