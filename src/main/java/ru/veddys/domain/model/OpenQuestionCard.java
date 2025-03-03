package ru.veddys.domain.model;

public class OpenQuestionCard {
    private final String question;
    private final String expectedAnswer;

    public OpenQuestionCard(String question, String expectedAnswer) {
        if (question == null || question.isEmpty()) {
            throw new IllegalArgumentException("question не может быть пустым");
        }
        if (expectedAnswer == null || expectedAnswer.isEmpty()) {
            throw new IllegalArgumentException("expectedAnswer не может быть пустым");
        }
        this.question = question;
        this.expectedAnswer = expectedAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public boolean checkAnswer(String answer) {
        if (answer == null) {
            return false;
        }
        return expectedAnswer.equals(answer);
    }
}


