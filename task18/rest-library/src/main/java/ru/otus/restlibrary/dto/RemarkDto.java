package ru.otus.restlibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.restlibrary.domain.Remark;

@AllArgsConstructor
@Data
public class RemarkDto {

    public static final RemarkDto DUMMY = new RemarkDto(0L, "N/A");

    private Long id;
    private String text;

    public RemarkDto(Remark remark) {
        id = remark.getId();
        text = remark.getText();
    }
}
