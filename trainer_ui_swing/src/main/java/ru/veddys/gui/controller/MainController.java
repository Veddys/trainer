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
        mainFrame = new JFrame("Time Management Tool");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setJMenuBar(prepareMenu());
        prepareMainPanelForListTask();
        mainFrame.setVisible(true);
    }

    private void prepareMainPanelForAddTask(Optional<OpenQuestionCard> taskForEdit) {
        if (mainPanel != null) {
            mainFrame.remove(mainPanel);
        }
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        mainPanel.add(new JLabel("Код"), layoutConstraints);
        JTextField codeField = new JTextField(15);
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 0;
        mainPanel.add(codeField, layoutConstraints);
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 1;
        mainPanel.add(new JLabel("Заголовок"), layoutConstraints);
        JTextField titleField = new JTextField(15);
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 1;
        mainPanel.add(titleField, layoutConstraints);
        JButton addButton = new JButton("Добавить");
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 2;
        layoutConstraints.gridwidth = 2;

        taskForEdit.ifPresent( t -> {
            codeField.setText(String.valueOf(t.getId()));
            titleField.setText(t.getTitle());
        });
        addButton.addActionListener(event -> {
            try {
                Long id = Long.parseLong(codeField.getText());
                String title = titleField.getText().trim();
                if(title.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "Заголовок не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                OpenQuestionCard question = new OpenQuestionCard(id, title);
                service.save(question);
                prepareMainPanelForListTask();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(mainFrame, "Код должен быть числом", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                // Ловим исключение, которое может возникнуть внутри конструктора OpenQuestionCard
                JOptionPane.showMessageDialog(mainFrame, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(addButton, layoutConstraints);
        mainFrame.add(mainPanel);
        SwingUtilities.updateComponentTreeUI(mainFrame);
    }

    private void prepareMainPanelForListTask() {
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

    private JMenuBar prepareMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu taskMenu = new JMenu("Task");
        JMenuItem addTask = new JMenuItem("Добавить задачу");
        addTask.addActionListener((event) -> {
            prepareMainPanelForAddTask(Optional.empty());
        });
        taskMenu.add(addTask);
        menuBar.add(taskMenu);
        JMenuItem listTask = new JMenuItem("Просмтреть задачи");
        listTask.addActionListener((event) -> {
            prepareMainPanelForListTask();
        });
        taskMenu.add(listTask);
        JMenuItem removeTask = new JMenuItem("Удалить задачу");
        removeTask.addActionListener((event) -> {
            OpenQuestionCard questionToDelete = (OpenQuestionCard)JOptionPane.showInputDialog(
                    mainFrame,
                    "Какую задачу хотите удалить?",
                    "Удаление задачи",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    service.getAll().toArray(),
                    null);
            service.delete(questionToDelete.getId());
            prepareMainPanelForListTask();
        });
        taskMenu.add(removeTask);
        JMenuItem editTask = new JMenuItem("Обновить задачу");
        editTask.addActionListener((event) -> {
            OpenQuestionCard questionToEdit = (OpenQuestionCard)JOptionPane.showInputDialog(
                    mainFrame,
                    "Какую задачу хотите изменить?",
                    "Удаление задачи",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    service.getAll().toArray(),
                    null);
            prepareMainPanelForAddTask(Optional.of(questionToEdit));
        });
        taskMenu.add(editTask);
        menuBar.add(taskMenu);
        return menuBar;
    }
}
