package ru.otus.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Food implements Serving {
    private String name;
}
