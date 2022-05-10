package ru.otus.hhexplorer.service;

import ru.otus.hhexplorer.exception.ApplicationException;

public interface CodeConverter {

    String getNativeProfessionalCode(String commonCode) throws ApplicationException;

    String getNativeAreaCode(String commonCode) throws ApplicationException;
}
