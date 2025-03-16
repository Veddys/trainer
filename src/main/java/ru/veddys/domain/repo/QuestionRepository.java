package ru.veddys.domain.repo;

import ru.veddys.domain.model.OpenQuestionCard;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
  List<OpenQuestionCard> findAll();
  Optional<OpenQuestionCard> findByID(String id);
  void add(OpenQuestionCard question);
  void update(OpenQuestionCard task);
  void remove(String id);
}
