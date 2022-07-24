package ru.otus.vacancykeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vacancycommon.dto.VacancyCommon;
import ru.otus.vacancykeeper.domain.Task;
import ru.otus.vacancykeeper.domain.Vacancy;
import ru.otus.vacancykeeper.domain.VacancyLink;
import ru.otus.vacancykeeper.dto.TaskDto;
import ru.otus.vacancykeeper.repository.TaskRepository;
import ru.otus.vacancykeeper.repository.VacancyLinkRepository;
import ru.otus.vacancykeeper.repository.VacancyRepository;
import ru.otus.vacancykeeper.util.VacancyConverter;

@Service
@RequiredArgsConstructor
public class VacancySaverImpl implements  VacancySaver {

    private final TaskRepository taskRepository;
    private final VacancyRepository vacancyRepository;
    private final VacancyLinkRepository vacancyLinkRepository;

    @Transactional
    @Override
    public boolean checkAndSaveVacancy(String scode, TaskDto taskDto, VacancyCommon vacancyCommon) {
        Task task = taskRepository.getById(taskDto.getId());
        Vacancy vacancy = vacancyRepository.findVacancyByScodeAndSid(scode, vacancyCommon.getId());
        if (null == vacancy) {
            vacancy = vacancyRepository.save(VacancyConverter.dtoToEntity(scode, vacancyCommon));
            vacancyLinkRepository.save(new VacancyLink(null, task, vacancy));
            return true;
        }
        VacancyLink link = vacancyLinkRepository.getVacancyLinkByTaskAndVacancy(task, vacancy);
        if (null == link) {
            vacancyLinkRepository.save(new VacancyLink(null, task, vacancy));
        }
        return null == link;
    }
}
