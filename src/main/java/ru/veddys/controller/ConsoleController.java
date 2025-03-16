package ru.veddys.controller;

import org.springframework.stereotype.Component;
import ru.veddys.domain.model.OpenQuestionCard;
import ru.veddys.domain.service.QuestionService;

import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleController {
  private static final String MENU = """
          Введите [1], чтобы показать все задачи
          Введите [2], чтобы добавить задачу
          Введите [3], чтобы удалить задачу
          Введите [4], чтобы найти задачу
          Введите [5], чтобы выйти
          """;
  private final QuestionService service;
  private final Scanner scanner;

  public ConsoleController(QuestionService service) {
    this.service = service;
    scanner = new Scanner(System.in);
  }

  public void interactWithUser() {
    while(true) {
      printMenu();
      String operationID = scanner.nextLine();
      switch (operationID) {
        case "1" -> printAllQuestions();
        case "2" -> addQuestion();
        case "3" -> removeQuestion();
        case "4" -> findQuestion();
        case "5" -> { return; }
        default -> System.out.println("Неизвестная команда");
      }
    }
  }

  private void printMenu() {
    System.out.println(MENU);
  }

  private void printAllQuestions() {
    System.out.println(service.getAll());
  }

  private void addQuestion() {
    System.out.println("Введите код задачи");
    String id = scanner.nextLine();
    System.out.println("Введите название задачи");
    String title = scanner.nextLine();
    OpenQuestionCard question = new OpenQuestionCard(id, title);
    service.save(question);
  }

  private void removeQuestion() {
    System.out.println("Введите код задачи, которую хотите удалить");
    String id = scanner.nextLine();
    Optional<OpenQuestionCard> question = service.getByID(id);
    if (question.isPresent()) {
      System.out.println("Введите [Y], если точно хотите удалить задачу " + question.get());
      String confirmation = scanner.nextLine();
      if ("Y".equals(confirmation)) {
        service.delete(question.get());
      }
    } else {
      System.out.println("Такой задачи найти не удалось");
    }
  }

  private void findQuestion() {
    System.out.println("Введите код задачи, которую хотите найти");
    String id = scanner.nextLine();
    Optional<OpenQuestionCard> question = service.getByID(id);
    if (question.isPresent()) {
      System.out.println(question.get());
    } else {
      System.out.println("Такой задачи найти не удалось");
    }
  }
}
