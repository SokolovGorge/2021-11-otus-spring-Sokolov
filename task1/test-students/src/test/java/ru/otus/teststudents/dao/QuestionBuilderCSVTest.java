package ru.otus.teststudents.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.teststudents.consts.AnswerType;
import ru.otus.teststudents.domain.Question;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс QuestionBuilderCSV")
class QuestionBuilderCSVTest {

    private final QuestionBuilderCSV instance = new QuestionBuilderCSV();

    @DisplayName("Создание Question")
    @Test
    void buildTest() {
        String[] csvStrings = new String[]{"Question1", "CHOICE", "Answer1|Answer2"};
        Question expQuestion = new Question("Question1", AnswerType.CHOICE, Arrays.asList("Answer1", "Answer2"));
        assertEquals(expQuestion, instance.build(csvStrings));
    }

    @DisplayName("Тест исключений")
    @Test
    void exceptionTest() {
        Exception exception1 = assertThrows(IllegalArgumentException.class,
                () -> instance.build("Test"));
        assertTrue(exception1.getMessage().endsWith("is not String[]"));

        Exception exception2 = assertThrows(IllegalArgumentException.class,
                () -> instance.build(new String[]{"Test1", "Test2"}));
        assertTrue(exception2.getMessage().endsWith("length not equal 3"));

    }

}
