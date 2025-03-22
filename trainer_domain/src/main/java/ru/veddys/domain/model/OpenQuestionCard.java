package ru.veddys.domain.model;

import ru.veddys.domain.util.ValidationUtil;

public class OpenQuestionCard {
    private final Long id;
    private final String question;
    private final String expectedAnswer;

    public OpenQuestionCard(Long id, String question, String expectedAnswer) {
        this.id = id;
        ValidationUtil.validateNotEmpty(question, "Question is empty.");
        ValidationUtil.validateNotEmpty(expectedAnswer, "Expected answer is empty.");

        this.question = question;
        this.expectedAnswer = expectedAnswer;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    public boolean checkAnswer(String answer) {
        ValidationUtil.validateNotEmpty(answer, "Answer is empty.");
        return answer.equals(expectedAnswer);
    }

    @Override
    public String toString() {
        return "QuestionCard {" +
                "id=" + id.toString() +
                ", question='" + question + '\'' +
                ", answer ='" + expectedAnswer + '\'' +
                '}';
    }
}