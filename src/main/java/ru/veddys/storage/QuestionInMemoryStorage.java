package ru.veddys.storage;

import org.springframework.stereotype.Repository;
import ru.veddys.domain.model.OpenQuestionCard;
import ru.veddys.domain.repo.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionInMemoryStorage implements QuestionRepository {
    private final List<OpenQuestionCard> questions = new ArrayList<>();

    @Override
    public List<OpenQuestionCard> findAll() {
        return new ArrayList<>(questions);
    }

    @Override
    public Optional<OpenQuestionCard> findById(Long id) {
        return questions.stream().filter(q -> q.getId().equals(id)).findFirst();
    }

    @Override
    public void add(OpenQuestionCard task) {
        questions.add(task);
    }

    @Override
    public void update(OpenQuestionCard task) {
        remove(task.getId());
        questions.add(task);
    }

    @Override
    public void remove(Long id) {
        questions.removeIf(q -> q.getId().equals(id));
    }
}