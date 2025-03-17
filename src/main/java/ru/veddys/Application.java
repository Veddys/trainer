package ru.veddys;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.veddys.config.SpringConfig;
import ru.veddys.controller.ConsoleController;

import javax.sql.DataSource;

public class Application {
  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    ConsoleController controller = context.getBean(ConsoleController.class);
    controller.interactWithUser();
    DataSource SpringConfig = context.getBean(DataSource.class);
    System.out.println("DataSource: " + SpringConfig);
  }
}






