package ru.veddys.storage;

import ru.veddys.domain.model.OpenQuestionCard;
import ru.veddys.domain.repo.QuestionRepository;

import java.util.*;

public class QuestionInMemoryStorage implements QuestionRepository {
  private final Map<String, OpenQuestionCard> question;

  public QuestionInMemoryStorage() {
    question = new HashMap<>();
  }

  @Override
  public List<OpenQuestionCard> findAll() {
    return question.values().stream().toList();
  }

  @Override
  public Optional<OpenQuestionCard> findByID(String id) {
    OpenQuestionCard openQuestionCard = question.get(id);
    if (Objects.nonNull(openQuestionCard)) {
      return Optional.of(openQuestionCard);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public void add(OpenQuestionCard task) {
    question.put(task.getID(), task);
  }

  @Override
  public void update(OpenQuestionCard task) {
    question.put(task.getID(), task);
  }

  @Override
  public void remove(String id) {
    question.remove(id);
  }
}
