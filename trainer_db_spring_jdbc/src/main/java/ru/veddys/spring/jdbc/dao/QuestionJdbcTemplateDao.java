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
            CREATE TABLE questions (ID BIGINT PRIMARY KEY, question VARCHAR(100), answer VARCHAR(50))
            """;
    private static final String FIND_ALL_QUERY = """
            SELECT ID, question, answer FROM questions
            """;
    private static final String FIND_BY_ID_QUERY = """
            SELECT ID, question, answer FROM questions WHERE ID=?
            """;
    private static final String INSERT_CARD_QUERY = """
            INSERT INTO questions(ID, question, answer) VALUES (?, ?, ?)
            """;
    private static final String UPDATE_CARD_QUERY = """
            UPDATE questions SET question=?, answer=? WHERE ID=?
            """;
    private static final String DELETE_CARD_QUERY = """
            DELETE FROM questions WHERE ID=?
            """;
    private final JdbcTemplate jdbcTemplate;

    public QuestionJdbcTemplateDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        initSchema();
    }


    @Override
    public List<OpenQuestionCard> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, (ResultSet rs, int count) -> new OpenQuestionCard(rs.getLong("ID"), rs.getString("question"), rs.getString("answer")));
    }

    @Override
    public Optional<OpenQuestionCard> findById(Long id) {
        List<OpenQuestionCard> cards = jdbcTemplate.query(FIND_BY_ID_QUERY, (ResultSet rs, int count) -> new OpenQuestionCard(rs.getLong("ID"), rs.getString("question"), rs.getString("answer")), id);
        return cards.isEmpty() ? Optional.empty() : Optional.of(cards.get(0));
    }

    @Override
    public void add(OpenQuestionCard card) {
        jdbcTemplate.update(INSERT_CARD_QUERY, card.getId(), card.getQuestion(), card.getExpectedAnswer());
    }

    @Override
    public void update(OpenQuestionCard card) {
        jdbcTemplate.update(UPDATE_CARD_QUERY, card.getQuestion(), card.getExpectedAnswer(), card.getId());
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update(DELETE_CARD_QUERY, id);
    }

    private void initSchema() {
        jdbcTemplate.update(DDL_QUERY);
    }
}
