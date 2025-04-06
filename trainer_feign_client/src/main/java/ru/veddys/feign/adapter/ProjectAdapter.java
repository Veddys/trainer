package ru.veddys.feign.adapter;

import org.springframework.stereotype.Component;
import ru.veddys.api.dto.OpenQuestionCardDto;
import ru.veddys.api.mapper.QuestionDtoMapper;
import ru.veddys.domain.model.OpenQuestionCard;
import ru.veddys.domain.repo.QuestionRepository;
import ru.veddys.feign.client.ProjectFeignClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProjectAdapter implements QuestionRepository {
    private final ProjectFeignClient feignClient;
    private final QuestionDtoMapper mapper;

    public ProjectAdapter(ProjectFeignClient feignClient, QuestionDtoMapper mapper) {
        this.feignClient = feignClient;
        this.mapper = mapper;
    }

    @Override
    public List<OpenQuestionCard> findAll() {
        List<OpenQuestionCardDto> projects = feignClient.list();
        return projects.stream()
                .map(mapper::mapToModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OpenQuestionCard> findByID(Long id) {
        List<OpenQuestionCardDto> projects = feignClient.list();
        return projects.stream()
                .map(mapper::mapToModel)
                .filter(project -> project.getId().equals(id))
                .findAny();
    }

    @Override
    public void add(OpenQuestionCard project) {
        feignClient.add(mapper.mapToDto(project));
    }

    @Override
    public void update(OpenQuestionCard project) {
        feignClient.update(mapper.mapToDto(project));
    }

    @Override
    public void remove(String id) {
        feignClient.remove(Long.valueOf(id));
    }
}
