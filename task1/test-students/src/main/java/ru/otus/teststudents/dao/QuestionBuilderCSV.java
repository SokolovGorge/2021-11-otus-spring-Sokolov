package ru.otus.teststudents.dao;

import ru.otus.teststudents.consts.AnswerType;
import ru.otus.teststudents.domain.Question;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestionBuilderCSV implements QuestionBuilder {

    @Override
    public Question build(Object obj) {
        if (!(obj instanceof String[])) {
            throw new IllegalArgumentException("Build question error: " + obj.getClass().getName() + "is not String[]");
        }
        String[] values = (String[])obj;
        if (values.length != 3) {
            throw new IllegalArgumentException("Build question error: string array length not equal 3");
        }
        return new Question(values[0], AnswerType.valueOf(values[1]), convertString2List(values[2]));
    }

    private List<String> convertString2List(String value) {
        if (null == value || value.isEmpty()) {
            return Collections.emptyList();
        }
        String[] values = value.split("\\|");
        return Arrays.asList(values);
    }

}
