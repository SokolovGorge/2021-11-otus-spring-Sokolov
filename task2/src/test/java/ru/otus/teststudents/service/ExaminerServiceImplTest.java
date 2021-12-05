package ru.otus.teststudents.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.teststudents.dao.QuestionsDao;
import ru.otus.teststudents.domain.Question;
import ru.otus.teststudents.exceptions.QuestionException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс ExaminerServiceImplTest")
@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
    private QuestionsDao questionsDao;
    @Mock
    private ReaderAnswerService readerAnswerService;
    @Mock
    private PrinterQuestionService printerQuestionService;


    private ExaminerService instance;

    @BeforeEach
    void setUp() {
        instance = new ExaminerServiceImpl(questionsDao, readerAnswerService, printerQuestionService, 3);
    }

    @DisplayName("Проведение тестирования")
    @Test
    void exam() throws QuestionException {
        Question q1 = new Question("Question1", Arrays.asList("Answer11", "Answer12"), "Answer11");
        Question q2 = new Question("Question2", Arrays.asList("Answer21", "Answer22"), "Answer21");
        Question q3 = new Question("Question3", Arrays.asList("Answer31", "Answer32"), "Answer31");
        given(questionsDao.readQuestions())
                .willReturn(Arrays.asList(q1, q2, q3));
        given(readerAnswerService.request(q1))
                .willReturn("Answer11");
        given(readerAnswerService.request(q2))
                .willReturn("Answer21");
        given(readerAnswerService.request(q3))
                .willReturn("Answer31");
        assertEquals(3, instance.exam());
    }

    @DisplayName("Оценка теста")
    @Test
    void estimationExam() {
        assertAll("estimation",
                () -> assertTrue(instance.estimationExam(5)),
                () -> assertFalse(instance.estimationExam(2)));
    }

}
