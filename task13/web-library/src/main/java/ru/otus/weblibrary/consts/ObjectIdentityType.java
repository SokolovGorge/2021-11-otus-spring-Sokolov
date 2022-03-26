package ru.otus.weblibrary.consts;

import lombok.Getter;

@Getter
public enum ObjectIdentityType {

    BOOK(1L);

    private final Long id;

    ObjectIdentityType(Long id) {
        this.id = id;
    }
}
