package ru.veddys.dao;

import org.springframework.stereotype.Repository;
import ru.veddys.domain.model.OpenQuestionCard;
import ru.veddys.domain.repo.QuestionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionJdbcDao implements QuestionRepository {
  private static final String DDL_QUERY = """
          CREATE TABLE questions (ID int AUTO_INCREMENT PRIMARY KEY,title VARCHAR(50))
          """;
  private static final String FIND_ALL_QUERY = """
          SELECT id, title FROM questions
          """;
  private static final String FIND_BY_ID_QUERY = """
          SELECT id, title FROM questions WHERE id = ?
          """;
  private static final String INSERT_TASK_QUERY = """
          INSERT INTO questions(id, title) VALUES (?, ?)
          """;
  private static final String UPDATE_TASK_QUERY = """
          UPDATE questions SET id=?, title=?
          """;
  private static final String DELETE_TASK_QUERY = """
          DELETE FROM questions WHERE id=?
          """;

  static {
    try {
      Class.forName("org.h2.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public QuestionJdbcDao() {
    initDataBase();
  }

  public void initDataBase() {
    Connection connection = null;
    try {
      connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(DDL_QUERY);
      statement.execute();
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection(connection);
    }
  }

  @Override
  public List<OpenQuestionCard> findAll() {
    Connection connection = null;
    List<OpenQuestionCard> questions = new ArrayList<>();
    try {
      connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        OpenQuestionCard question = new OpenQuestionCard(
                resultSet.getLong("id"),  // Используем getLong
                resultSet.getString("title")
        );
        questions.add(question);
      }
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection(connection);
    }
    return questions;
  }


  @Override
  public Optional<OpenQuestionCard> findByID(Long id) {
    Connection connection = null;
    List<OpenQuestionCard> OpenQuestionCard = new ArrayList<>();
    try {
      connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY);
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        OpenQuestionCard question = new OpenQuestionCard(resultSet.getLong("id"),
                resultSet.getString("title"));
        OpenQuestionCard.add(question);
      }
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection(connection);
    }
    return OpenQuestionCard.isEmpty() ? Optional.empty() : Optional.of(OpenQuestionCard.get(0));
  }

  @Override
  public void add(OpenQuestionCard task) {
    Connection connection = null;
    try {
      connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(INSERT_TASK_QUERY);
      statement.setLong(1, task.getId());
      statement.setString(2, task.getTitle());
      statement.execute();
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection(connection);
    }
  }

  @Override
  public void update(OpenQuestionCard task) {
    Connection connection = null;
    try {
      connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(UPDATE_TASK_QUERY);
      statement.setLong(1, task.getId());
      statement.setString(2, task.getTitle());
      statement.execute();
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection(connection);
    }
  }

  @Override
  public void remove(String id) {
    Connection connection = null;
    try {
      connection = getConnection();
      PreparedStatement statement = connection.prepareStatement(DELETE_TASK_QUERY);
      statement.setString(1, id);
      statement.execute();
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection(connection);
    }
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection("jdbc:h2:mem:question;DB_CLOSE_DELAY=-1",
            "admin", "admin");
  }

  private void closeConnection(Connection connection) {
    if (connection == null) return;
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
