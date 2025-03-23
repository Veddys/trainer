package ru.veddys.gui.controller;

import ru.veddys.domain.model.OpenQuestionCard;
import ru.veddys.domain.service.QuestionService;
import ru.veddys.gui.model.QuestionTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class MainController implements Runnable {

    private final QuestionService service;
    private JFrame mainFrame;
    private JPanel mainPanel;

    public MainController(QuestionService service) {
        this.service = service;
    }

    @Override
    public void run() {
        mainFrame = new JFrame("Questions manager");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setJMenuBar(prepareMenu());
        prepareMainPanelForListCard();
        mainFrame.setVisible(true);
    }

    private JMenuBar prepareMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu taskMenu = new JMenu("Question");
        JMenuItem addTask = new JMenuItem("Добавить вопрос");
        addTask.addActionListener((event) -> {
            prepareMainPanelForAddCard(Optional.empty());
        });
        taskMenu.add(addTask);
        menuBar.add(taskMenu);
        JMenuItem listTask = new JMenuItem("Все вопросы");
        listTask.addActionListener((event) -> {
            prepareMainPanelForListCard();
        });
        taskMenu.add(listTask);
        JMenuItem removeTask = getRemoveMenuItem();
        taskMenu.add(removeTask);
        JMenuItem editTask = getEditMenuItem();
        taskMenu.add(editTask);
        menuBar.add(taskMenu);
        return menuBar;
    }

    private JMenuItem getEditMenuItem() {
        JMenuItem editTask = new JMenuItem("Обновить вопрос");
        editTask.addActionListener((event) -> {
            OpenQuestionCard cardToDelete = (OpenQuestionCard) JOptionPane.showInputDialog(
                    mainFrame,
                    "Какой вопрос хотите изменить?",
                    "Измененить вопрос",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    service.getAll().toArray(),
                    null);
            prepareMainPanelForAddCard(Optional.of(cardToDelete));
        });
        return editTask;
    }

    private JMenuItem getRemoveMenuItem() {
        JMenuItem removeTask = new JMenuItem("Удалить вопрос");
        removeTask.addActionListener((event) -> {
            OpenQuestionCard cardToDelete = (OpenQuestionCard) JOptionPane.showInputDialog(
                    mainFrame,
                    "Какой вопрос хотите удалить?",
                    "Удаление вопроса",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    service.getAll().toArray(),
                    null);
            service.delete(cardToDelete);
            prepareMainPanelForListCard();
        });
        return removeTask;
    }

    private void prepareMainPanelForListCard() {

        if (mainPanel != null) {
            mainFrame.remove(mainPanel);
        }
        mainPanel = new JPanel(new BorderLayout());
        JTable table = new JTable(new QuestionTableModel(service.getAll()));
        mainPanel.add(table, BorderLayout.CENTER);
        mainPanel.add(table.getTableHeader(), BorderLayout.NORTH);
        mainFrame.add(mainPanel);
        SwingUtilities.updateComponentTreeUI(mainFrame);

    }

    private void prepareMainPanelForAddCard(Optional<OpenQuestionCard> cardToEdit) {
        if (mainPanel != null) {
            mainFrame.remove(mainPanel);
        }
        mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        mainPanel.add(new JLabel("Номер"), layoutConstraints);

        JTextField idField = new JTextField(15);
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 0;
        mainPanel.add(idField, layoutConstraints);

        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 1;
        mainPanel.add(new JLabel("Вопрос"), layoutConstraints);

        JTextField questionField = new JTextField(15);
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 1;
        mainPanel.add(questionField, layoutConstraints);

        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 2;
        mainPanel.add(new JLabel("Ответ"), layoutConstraints);

        JTextField expectedAnswerField = new JTextField(15);
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 2;
        mainPanel.add(expectedAnswerField, layoutConstraints);

        JButton addButton = new JButton("Добавить");
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 3;
        layoutConstraints.gridwidth = 2;
        cardToEdit.ifPresent(card -> {
            idField.setText(String.valueOf(card.getId()));
            questionField.setText(card.getQuestion());
            expectedAnswerField.setText(card.getExpectedAnswer());
        });
        addButton.addActionListener(event -> {
            OpenQuestionCard card = new OpenQuestionCard(Long.valueOf(idField.getText()), questionField.getText(), expectedAnswerField.getText());
            service.save(card);
            prepareMainPanelForListCard();
        });
        mainPanel.add(addButton, layoutConstraints);
        mainFrame.add(mainPanel);
        SwingUtilities.updateComponentTreeUI(mainFrame);
    }
}
