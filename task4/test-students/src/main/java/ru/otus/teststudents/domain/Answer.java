package ru.otus.teststudents.domain;

import java.util.Objects;

public class Answer {
    private final String answer;
    private final boolean correct;

    public Answer(String answer, boolean correct) {
        this.answer = answer;
        this.correct = correct;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isCorrect() {
        return correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer1 = (Answer) o;
        return correct == answer1.correct && answer.equals(answer1.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answer, correct);
    }
}
