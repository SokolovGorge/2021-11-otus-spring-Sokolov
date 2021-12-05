package ru.otus.teststudents.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.teststudents.domain.Question;
import ru.otus.teststudents.exceptions.QuestionException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс QuestionBuilderCSV")
class QuestionBuilderCSVTest {

    private final QuestionBuilderCSV instance = new QuestionBuilderCSV();

    @DisplayName("Создание Question")
    @Test
    void buildTest() {
        String[] csvStrings = new String[]{"Question1", "Answer1|Answer2", "Answer1"};
        Question expQuestion = new Question("Question1", Arrays.asList("Answer1", "Answer2"), "Answer1");
        try {
            Question question =  instance.build(csvStrings);
            assertEquals(expQuestion, question);
        } catch (QuestionException ex) {
            fail(ex);
        }
    }

    @DisplayName("Тест исключений")
    @Test
    void exceptionTest() {
        Exception exception = assertThrows(QuestionException.class,
                () -> instance.build(new String[]{"Test1", "Test2"}));
        assertTrue(exception.getMessage().endsWith("length not equal 3"));

    }

}
