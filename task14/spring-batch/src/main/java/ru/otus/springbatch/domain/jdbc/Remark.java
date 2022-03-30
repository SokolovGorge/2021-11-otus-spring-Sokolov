package ru.otus.springbatch.domain.jdbc;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Remark {

    private Long id;
    private Long bookId;
    private String text;
}
