package ru.otus.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Drink implements Serving {
    private String name;
}
