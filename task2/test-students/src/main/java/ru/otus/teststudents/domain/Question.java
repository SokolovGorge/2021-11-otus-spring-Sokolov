package ru.otus.teststudents.domain;

import java.util.List;
import java.util.Objects;

public class Question {

    private final String question;
    private final List<String> answers;
    private final String rightAnswer;

    public Question(String question, List<String> answers, String rightAnswer) {
        this.question = question;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return question.equals(question1.question) && answers.equals(question1.answers) && rightAnswer.equals(question1.rightAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answers, rightAnswer);
    }
}
