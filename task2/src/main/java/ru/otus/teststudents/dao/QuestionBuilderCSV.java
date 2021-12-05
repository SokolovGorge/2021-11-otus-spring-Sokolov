package ru.otus.teststudents.dao;

import org.springframework.stereotype.Component;
import ru.otus.teststudents.domain.Question;
import ru.otus.teststudents.exceptions.QuestionException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class QuestionBuilderCSV implements QuestionBuilder {

    @Override
    public Question build(String[] values) throws QuestionException {
         if (values.length != 3) {
            throw new QuestionException("Build question error: string array length not equal 3");
        }
        return new Question(values[0], convertString2List(values[1]), values[2]);
    }

    private List<String> convertString2List(String value) {
        if (null == value || value.isEmpty()) {
            return Collections.emptyList();
        }
        String[] values = value.split("\\|");
        return Arrays.asList(values);
    }

}
