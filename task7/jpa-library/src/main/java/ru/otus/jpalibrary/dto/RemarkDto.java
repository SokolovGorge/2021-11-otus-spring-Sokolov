package ru.otus.jpalibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.jpalibrary.domain.Remark;


@AllArgsConstructor
@Data
public class RemarkDto {

    private Long id;
    private String text;

    public RemarkDto(Remark remark) {
        id = remark.getId();
        text = remark.getText();
    }
}
