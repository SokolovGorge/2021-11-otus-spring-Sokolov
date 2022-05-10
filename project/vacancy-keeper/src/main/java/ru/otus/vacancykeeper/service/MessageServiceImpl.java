package ru.otus.vacancykeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.otus.vacancykeeper.config.MessageConfig;
import ru.otus.vacancykeeper.domain.Task;
import ru.otus.vacancykeeper.dto.MessageInfo;
import ru.otus.vacancykeeper.dto.TaskDto;
import ru.otus.vacancykeeper.dto.VacancyDto;
import ru.otus.vacancykeeper.repository.TaskRepository;

import javax.transaction.Transaction;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private  final RestTemplate restTemplate;
    private  final MessageConfig messageConfig;
    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public void sendAlarm(TaskDto taskDto, VacancyDto vacancyDto) {
        final Task task = taskRepository.getById(taskDto.getId());
        restTemplate.postForObject(generateUrl(), generateMessage(task, vacancyDto), Object.class);
    }

    private MessageInfo generateMessage(Task task, VacancyDto vacancyDto) {
        final MessageInfo messageInfo = new MessageInfo();
        messageInfo.setFirstname(task.getUser().getFirstname());
        messageInfo.setSurname(task.getUser().getSurname());
        messageInfo.setPathname(task.getUser().getPathname());
        messageInfo.setEmail(task.getUser().getEmail());
        messageInfo.setTelephone(task.getUser().getTelephone());
        messageInfo.setVacancy(vacancyDto.getName());
        messageInfo.setSourceUrl(vacancyDto.getSourceURL());
        return messageInfo;
    }

    private String generateUrl() {
        return "http://" + messageConfig.getMessageServiceName() + "/api/send/";
    }
}
