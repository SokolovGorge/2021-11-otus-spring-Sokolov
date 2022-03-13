package ru.otus.weblibrary.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class RemarkEditModel {

    private long bookId;

    @NotBlank(message = "Отзыв должен быть заполнен")
    private String text;
}
