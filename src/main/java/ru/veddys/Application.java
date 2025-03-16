package ru.veddys;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.veddys.controller.ConsoleController;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private final ConsoleController consoleController;

    public Application(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        consoleController.interactWithUser();
    }
}