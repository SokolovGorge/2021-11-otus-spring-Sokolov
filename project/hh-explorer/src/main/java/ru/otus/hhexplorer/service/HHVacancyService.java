package ru.otus.hhexplorer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import ru.otus.hhexplorer.config.VacancyConfig;
import ru.otus.hhexplorer.dto.common.RequestItem;
import ru.otus.hhexplorer.dto.hh.HHVacancyPage;
import ru.otus.hhexplorer.exception.ApplicationException;
import ru.otus.vacancycommon.dto.VacancyPackage;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HHVacancyService implements VacancyService {

    private final VacancyConfig vacancyConfig;
    private final CodeConverter codeConverter;
    private final HHVacancyConverter vacancyConverter;
    private final RestOperations rest = new RestTemplate();

    @Override
    public VacancyPackage getVacancies(RequestItem requestItem) throws ApplicationException {
        if (null == requestItem) {
            throw new ApplicationException("Undefined requestItem");
        }
        if (null == requestItem.getProfCode()) {
            throw new ApplicationException("Undefined professional code");
        }
        if (null == requestItem.getAreaCode()) {
            throw new ApplicationException("Undefined area code");
        }
        return vacancyConverter.convert(Objects.requireNonNull(rest.getForObject(buildUrl(requestItem), HHVacancyPage.class)));
    }

    private String buildUrl(RequestItem requestItem) throws ApplicationException {
        final StringBuilder sb = new StringBuilder(vacancyConfig.getServiceVacancyUrl())
                .append("?professional_role=")
                .append(codeConverter.getNativeProfessionalCode(requestItem.getProfCode()))
                .append("&area=")
                .append(codeConverter.getNativeAreaCode(requestItem.getAreaCode()));
        if (requestItem.getWords() != null && !"".equals(requestItem.getWords().trim())) {
            String[] words = requestItem.getWords().split("\\s");
            for (String word : words) {
                sb.append("&text=")
                        .append(word);
            }
        }
        if (requestItem.getPage() != null) {
            sb.append("&page=")
                    .append(requestItem.getPage());
        }
        return sb.toString();
    }
}
