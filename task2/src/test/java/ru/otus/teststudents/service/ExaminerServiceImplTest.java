package ru.otus.teststudents.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.teststudents.dao.ReaderQuestionsDao;
import ru.otus.teststudents.domain.Question;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс ExaminerServiceImplTest")
@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
    private ReaderQuestionsDao readerQuestionsDao;
    @Mock
    private ReaderAnswerService readerAnswerDao;
    @Mock
    private PublisherService publisherDao;


    private ExaminerService instance;

    @BeforeEach
    void setUp() {
        instance = new ExaminerServiceImpl(readerQuestionsDao, readerAnswerDao, publisherDao, 3);
    }

    @DisplayName("Проведение тестирования")
    @Test
    void exam() {
        Question q1 = new Question("Question1", Arrays.asList("Answer11", "Answer12"), "Answer11");
        Question q2 = new Question("Question2", Arrays.asList("Answer21", "Answer22"), "Answer21");
        Question q3 = new Question("Question3", Arrays.asList("Answer31", "Answer32"), "Answer31");
        given(readerQuestionsDao.readQuestions())
                .willReturn(Arrays.asList(q1, q2, q3));
        given(readerAnswerDao.request(q1))
                .willReturn("Answer11");
        given(readerAnswerDao.request(q2))
                .willReturn("Answer21");
        given(readerAnswerDao.request(q3))
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
