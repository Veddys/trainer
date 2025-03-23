package ru.veddys.console.controller;

import org.springframework.stereotype.Component;
import ru.veddys.domain.model.OpenQuestionCard;
import ru.veddys.domain.service.QuestionService;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleController {
    private final QuestionService service;
    private final Scanner scanner;

    private static final String MENU = """
            Меню:
            Введите [1], чтобы показать все вопросы
            Введите [2], чтобы добавить вопрос
            Введите [3], чтобы удалить вопрос
            Введите [4], чтобы найти вопрос
            Введите [5], чтобы выйти""";

    public ConsoleController(QuestionService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void interactWithUser() {
        while (true) {
            printMenu();
            String opCode = scanner.nextLine();
            switch (opCode) {
                case "1" -> printAll();
                case "2" -> addNewQuestion();
                case "3" -> removeCard();
                case "4" -> findCard();
                case "5" -> {
                    return;
                }
            }
        }
    }

    private void printMenu() {
        System.out.println(MENU);
    }

    private void printAll() {
        System.out.println(service.getAll());
    }

    private void addNewQuestion() {
        System.out.println("Введите номер вопроса");
        Long id = Long.valueOf(scanner.nextLine());
        System.out.println("Введите вопрос");
        String question = scanner.nextLine();
        System.out.println("Введите ответ");
        String answer = scanner.nextLine();
        OpenQuestionCard card = null;
        try {
            card = new OpenQuestionCard(id, question, answer);
        } catch (Exception e) {
            System.out.println("Не удалось добавить вопрос");
        }
        if (Objects.nonNull(card)) {
            service.save(card);
            System.out.println("Вопрос добавлена");
        }
    }

    private void removeCard() {
        System.out.println("Введите номер вопроса, который хотите удалить");
        Long id = Long.valueOf(scanner.nextLine());
        Optional<OpenQuestionCard> card = service.getById(id);
        if (card.isPresent()) {
            System.out.println("Введите [Y], если хотите удалить вопрос: " + card.get());
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("y")) {
                service.delete(card.get());
                System.out.println("Вопрос была удален");
            } else {
                System.out.println("Вопрос не удален");
            }
        } else {
            System.out.println("Такого вопрос найти не удалось");
        }
    }

    private void findCard() {
        System.out.println("Введите номер вопроса, который хотите найти");
        Long id = Long.valueOf(scanner.nextLine());
        Optional<OpenQuestionCard> card = service.getById(id);
        if (card.isPresent()) {
            System.out.println(card.get());
        } else {
            System.out.println("Такого вопроса найти не удалось");
        }
    }

}
