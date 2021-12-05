package ru.otus.teststudents.domain;

import ru.otus.teststudents.consts.AnswerType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Question {

    private final String question;
    private final AnswerType answerType;
    private final List<String> answers;

    public Question(String question, AnswerType answerType, List<String> answers) {
        this.question = question;
        this.answerType = answerType;
        this.answers = answers;
        if (this.answerType == AnswerType.CHOICE && this.answers.isEmpty()) {
            throw new IllegalArgumentException("Question with choice must have answers.");
        }
    }

    public String getQuestion() {
        return question;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public List<String> getAnswers() {
        return answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return Objects.equals(question, question1.question) && answerType == question1.answerType && Objects.equals(answers, question1.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answerType, answers);
    }
}
