package ru.veddys.dao;

import org.springframework.stereotype.Repository;
import ru.veddys.domain.model.OpenQuestionCard;
import ru.veddys.domain.repo.QuestionRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionJdbcDao implements QuestionRepository {
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
            DELETE FROM cards WHERE ID=?
            """;
    private final DataSource dataSource;

    public QuestionJdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
        initDataBase();
    }

    public void initDataBase() {
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(DDL_QUERY)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<OpenQuestionCard> findAll() {
        List<OpenQuestionCard> cards = new ArrayList<>();
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY); ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                OpenQuestionCard card = new OpenQuestionCard(rs.getLong("ID"), rs.getString("question"), rs.getString("answer"));
                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Optional<OpenQuestionCard> findById(Long id) {
        List<OpenQuestionCard> cards = new ArrayList<>();
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                OpenQuestionCard card = new OpenQuestionCard(rs.getLong("ID"), rs.getString("question"), rs.getString("answer"));
                cards.add(card);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards.isEmpty() ? Optional.empty() : Optional.of(cards.getFirst());
    }

    @Override
    public void add(OpenQuestionCard card) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(INSERT_CARD_QUERY)) {
            statement.setLong(1, card.getId());
            statement.setString(2, card.getQuestion());
            statement.setString(3, card.getExpectedAnswer());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(OpenQuestionCard card) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_CARD_QUERY)) {
            statement.setString(1, card.getQuestion());
            statement.setString(2, card.getExpectedAnswer());
            statement.setLong(3, card.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Long id) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_CARD_QUERY)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
