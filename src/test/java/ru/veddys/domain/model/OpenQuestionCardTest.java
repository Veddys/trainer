package ru.veddys.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class OpenQuestionCardTest {
    private OpenQuestionCard card;

    @BeforeEach
    void setUp() {
        card = new OpenQuestionCard("Как у вас дела?", "Замечательно!");
    }

    @Test
    @DisplayName("Создание OpenQuestionCard с корректными question и expectedAnswer проходит успешно")
    void having_correctQuestionAndExpectedAnswer_when_newOpenQuestionCard_then_created() {
        String question = "Сколько будет 2+2?";
        String expected = "4";
        OpenQuestionCard card = new OpenQuestionCard(question, expected);
        Assertions.assertEquals(question, card.getQuestion());
    }

    @Test
    @DisplayName("Создание OpenQuestionCard с question равным null выбрасывает исключение")
    void having_nullQuestion_when_newOpenQuestionCard_then_exceptionThrown() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new OpenQuestionCard(null, "Ответ"));
    }

    @Test
    @DisplayName("Создание OpenQuestionCard с expectedAnswer равным null выбрасывает исключение")
    void having_nullExpectedAnswer_when_newOpenQuestionCard_then_exceptionThrown() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new OpenQuestionCard("Вопрос", null));
    }

    @Test
    @DisplayName("Вызов checkAnswer с ожидаемым ответом возвращает true")
    void having_expectedAnswer_when_checkAnswer_then_trueReturned() {
        Assertions.assertTrue(card.checkAnswer("Замечательно!"));
    }

    @Test
    @DisplayName("Вызов checkAnswer с неверным ответом возвращает false")
    void having_incorrectAnswer_when_checkAnswer_then_falseReturned() {
        Assertions.assertFalse(card.checkAnswer("Могло быть и лучше"));
    }

    @Test
    @DisplayName("Вызов checkAnswer с null возвращает false")
    void having_nullAnswer_when_checkAnswer_then_falseReturned() {
        Assertions.assertFalse(card.checkAnswer(null));
    }
}
