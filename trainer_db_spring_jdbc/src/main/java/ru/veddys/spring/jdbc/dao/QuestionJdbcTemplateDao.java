package ru.veddys.spring.jdbc.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.veddys.domain.model.OpenQuestionCard;
import ru.veddys.domain.repo.QuestionRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionJdbcTemplateDao implements QuestionRepository {
    private static final String DDL_QUERY = """
          CREATE TABLE questions (ID INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(50) NOT NULL)
          """;
    private static final String FIND_ALL_QUERY = """
          SELECT id, title FROM questions
          """;
    private static final String FIND_BY_ID_QUERY = """
          SELECT id, title FROM questions WHERE id = ?
          """;
    private static final String INSERT_QUERY = """
          INSERT INTO questions (id, title) VALUES (?, ?)
          """;
    private static final String UPDATE_QUERY = """
          UPDATE questions SET title = ? WHERE id = ?
          """;
    private static final String DELETE_QUERY = """
          DELETE FROM questions WHERE id = ?
          """;

    private final JdbcTemplate jdbcTemplate;

    public QuestionJdbcTemplateDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        initSchema();
    }

    @Override
    public List<OpenQuestionCard> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY,
                (ResultSet rs, int rowNum) ->
                        new OpenQuestionCard(
                                rs.getLong("id"),
                                rs.getString("title"))
        );
    }

    @Override
    public Optional<OpenQuestionCard> findByID(Long id) {
        List<OpenQuestionCard> questions = jdbcTemplate.query(FIND_BY_ID_QUERY,
                (ResultSet rs, int rowNum) ->
                        new OpenQuestionCard(
                                rs.getLong("id"),
                                rs.getString("title")), id);
        return questions.isEmpty() ? Optional.empty() : Optional.of(questions.get(0));
    }

    @Override
    public void add(OpenQuestionCard question) {
        jdbcTemplate.update(INSERT_QUERY, question.getId(), question.getTitle());
    }

    @Override
    public void update(OpenQuestionCard question) {
        jdbcTemplate.update(UPDATE_QUERY, question.getId(), question.getTitle());
    }

    @Override
    public void remove(String id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    private void initSchema() {
        jdbcTemplate.update(DDL_QUERY);
    }
}
