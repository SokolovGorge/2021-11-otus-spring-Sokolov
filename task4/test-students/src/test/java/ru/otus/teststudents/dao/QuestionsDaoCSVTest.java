package ru.otus.teststudents.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.teststudents.config.QuestionStorageConfig;
import ru.otus.teststudents.domain.Answer;
import ru.otus.teststudents.domain.Question;
import ru.otus.teststudents.exceptions.QuestionException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("Тестирование чтения csv файла вопросов")
@SpringBootTest
class QuestionsDaoCSVTest {

    @MockBean
    private QuestionStorageConfig storageConfig;

    @Autowired
    private QuestionsDao questionsDao;

    @DisplayName("Тест чтения csv файла")
    @Test
    void testReadCSVFile() throws QuestionException {
        given(storageConfig.getQuestionFileName()).willReturn("questions.csv");

        int expSize = 5;
        List<Question> result = questionsDao.readQuestions();
        assertEquals(expSize, result.size());

        Question expQuestion = new Question("Under what conditions is an object's finalize() method invoked by the garbage collector?",
                List.of(
                        new Answer("When it detects that the object has become unreachable.", true),
                        new Answer("As soon as object is set as null.", false),
                        new Answer("At fixed intervalm it checks for null value.", false),
                        new Answer("None of the above.", false)
                )
        );
        assertTrue(result.contains(expQuestion));
    }

}
