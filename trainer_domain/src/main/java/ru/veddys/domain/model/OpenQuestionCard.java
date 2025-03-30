package ru.veddys.domain.model;

import ru.veddys.domain.utility.ValidationUtil;

import java.time.ZonedDateTime;

public class OpenQuestionCard {
  private final Long id;
  private final String title;

  public OpenQuestionCard(Long id, String title) {
    if (id <= 0) {
      throw new IllegalArgumentException("id должен быть положительным числом");
    }
    ValidationUtil.validateNotEmpty(title, "title не может быть пустым");
    this.id = id;
    this.title = title;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  @Override
  public String toString() {
    return "OpenQuestionCard{" +
            "id=" + id +
            ", title='" + title + '\'' +
            '}';
  }
}
