package ru.veddys.domain.service;

import org.springframework.stereotype.Service;
import ru.veddys.domain.model.OpenQuestionCard;
import ru.veddys.domain.repo.QuestionRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuestionService {
  private final QuestionRepository repository;

  public QuestionService(QuestionRepository repository) {
    this.repository = repository;
  }

  public List<OpenQuestionCard> getAll() {
    return repository.findAll();
  }

  public Optional<OpenQuestionCard> getByID(String id) {
    if (Objects.isNull(id)) {
      return Optional.empty();
    }
    return repository.findByID(id);
  }

  public boolean contains(OpenQuestionCard task) {
    if (isQuestionInvalid(task)) {
      return false;
    }
    return repository.findByID(task.getID()).isPresent();
  }

  public void save(OpenQuestionCard task) {
    if (isQuestionInvalid(task)) {
      return;
    }
    if (contains(task)) {
      repository.update(task);
    } else {
      repository.add(task);
    }
  }

  public void delete(OpenQuestionCard task) {
    if (isQuestionInvalid(task)) {
      return;
    }
    repository.remove(task.getID());
  }

  private boolean isQuestionInvalid(OpenQuestionCard task) {
    return Objects.isNull(task) || Objects.isNull(task.getID());
  }
}
