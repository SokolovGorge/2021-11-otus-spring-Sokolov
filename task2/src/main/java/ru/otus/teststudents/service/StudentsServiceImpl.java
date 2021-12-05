package ru.otus.teststudents.service;

import org.springframework.stereotype.Service;
import ru.otus.teststudents.exceptions.QuestionException;

@Service
public class StudentsServiceImpl implements StudentsService {

    private final ExaminerService examinerService;

    public StudentsServiceImpl(ExaminerService examinerService) {
        this.examinerService = examinerService;
    }

    @Override
    public void examStudent() throws QuestionException {
        int goodAnswers = examinerService.exam();
        boolean result = examinerService.estimationExam(goodAnswers);
        examinerService.publishResult(result);

    }
}
