package ru.otus.teststudents.dao;

import org.springframework.stereotype.Component;
import ru.otus.teststudents.domain.Answer;
import ru.otus.teststudents.domain.Question;
import ru.otus.teststudents.exceptions.QuestionException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class QuestionBuilderCSV implements QuestionBuilder {

    @Override
    public Question build(String[] values) throws QuestionException {
         if (values.length != 2) {
            throw new QuestionException("Build question error: string array length not equal 2");
        }
        return new Question(values[0], convertString2List(values[1]));
    }

    private List<Answer> convertString2List(String value) throws QuestionException {
        if (null == value || value.isEmpty()) {
            return Collections.emptyList();
        }
        String[] values = value.split("\\|");
        List<Answer> result = new ArrayList<>();
        for (String st : values) {
            result.add(convertToAnswer(st));
        }
        return result;
    }

    private Answer convertToAnswer(String st) throws QuestionException {
        char ch = st.charAt(0);
        if (ch != '0' && ch != '1') {
            throw new QuestionException("Unexpected first char in answer: " + ch);
        }
        return new Answer(st.substring(1), ch == '1');
    }

}
