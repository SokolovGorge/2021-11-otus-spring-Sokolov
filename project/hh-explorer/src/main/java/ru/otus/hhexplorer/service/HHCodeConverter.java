package ru.otus.hhexplorer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hhexplorer.config.ConverterConfig;
import ru.otus.hhexplorer.exception.ApplicationException;

@Service
@RequiredArgsConstructor
public class HHCodeConverter implements CodeConverter {

    private final ConverterConfig converterConfig;

    @Override
    public String getNativeProfessionalCode(String commonCode) throws ApplicationException {
        String nativeCode = converterConfig.getProfMap().get(commonCode);
        if (null == nativeCode) {
            throw new ApplicationException("Unknown professional code: " + commonCode);
        }
        return nativeCode;
    }

    @Override
    public String getNativeAreaCode(String commonCode) throws ApplicationException {
        String nativeCode = converterConfig.getAreaMap().get(commonCode);
        if (null == nativeCode) {
            throw new ApplicationException("Unknown area code: " + commonCode);
        }
        return nativeCode;
    }
}
