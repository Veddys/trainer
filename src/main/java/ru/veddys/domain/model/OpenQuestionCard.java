package ru.veddys.domain.model;

public class OpenQuestionCard {
    private final Long id;
    private final String question;
    private final String expectedAnswer;

    public OpenQuestionCard(Long id, String question, String expectedAnswer) {
        this.id = id;
        this.question = question;
        this.expectedAnswer = expectedAnswer;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public boolean checkAnswer(String answer) {
        return expectedAnswer.equalsIgnoreCase(answer.trim());
    }
}
