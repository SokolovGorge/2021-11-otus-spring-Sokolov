package ru.otus.mongolibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.mongolibrary.domain.Remark;

@AllArgsConstructor
@Data
public class RemarkDto {

    private String id;
    private String text;

    public RemarkDto(Remark remark) {
        id = remark.getId();
        text = remark.getText();
    }
}
