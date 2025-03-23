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

    public Optional<OpenQuestionCard> getById(Long id) {
        if (Objects.isNull(id)) return Optional.empty();
        return repository.findById(id);
    }

    public boolean contains(OpenQuestionCard card) {
        if (iscardInvalid(card)) {
            return false;
        }
        return repository.findById(card.getId()).isPresent();
    }

    public void save(OpenQuestionCard card) {
        if (iscardInvalid(card)) return;
        if (contains(card)) repository.update(card);
        else repository.add(card);
    }

    public void delete(OpenQuestionCard card) {
        if (iscardInvalid(card)) return;
        repository.remove(card.getId());
    }


    private boolean iscardInvalid(OpenQuestionCard card) {
        return Objects.isNull(card) || Objects.isNull(card.getId());
    }

}
