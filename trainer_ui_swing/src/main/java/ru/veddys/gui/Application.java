package ru.veddys.gui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.veddys.domain.service.QuestionService;
import ru.veddys.gui.config.SpringConfig;
import ru.veddys.gui.controller.MainController;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        QuestionService questionService = context.getBean(QuestionService.class);
        SwingUtilities.invokeLater(new MainController(questionService));
    }
}
