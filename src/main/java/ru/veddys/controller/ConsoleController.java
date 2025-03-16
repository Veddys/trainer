package ru.veddys.controller;

import org.springframework.stereotype.Controller;
import ru.veddys.domain.model.OpenQuestionCard;
import ru.veddys.domain.service.QuestionService;

import java.util.Optional;
import java.util.Scanner;

@Controller
public class ConsoleController {
    private final QuestionService service;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleController(QuestionService service) {
        this.service = service;
    }

    public void interactWithUser() {
        System.out.println("Добро пожаловать в систему вопросов!");

        while (true) {
            System.out.println("1 - Показать вопросы, 2 - Добавить, 3 - Найти, 4 - Выйти");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> service.getAll().forEach(q -> System.out.println(q.getId() + ": " + q.getQuestion()));
                case 2 -> addQuestion();
                case 3 -> findQuestion();
                case 4 -> {
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Некорректный ввод!");
            }
        }
    }

    private void addQuestion() {
        System.out.print("Введите ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Введите вопрос: ");
        String question = scanner.nextLine();

        System.out.print("Введите ожидаемый ответ: ");
        String answer = scanner.nextLine();

        service.save(new OpenQuestionCard(id, question, answer));
        System.out.println("Вопрос добавлен!");
    }

    private void findQuestion() {
        System.out.print("Введите ID вопроса: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Optional<OpenQuestionCard> question = service.getById(id);
        question.ifPresentOrElse(
            q -> System.out.println("Вопрос: " + q.getQuestion()),
            () -> System.out.println("Вопрос не найден.")
        );
    }
}
