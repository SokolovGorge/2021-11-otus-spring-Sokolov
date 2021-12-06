package ru.otus.teststudents.domain;

import java.util.List;
import java.util.Objects;

public class Question {

    private final String question;
    private final List<Answer> answers;

    public Question(String question, List<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return question.equals(question1.question) && answers.equals(question1.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answers);
    }
}
