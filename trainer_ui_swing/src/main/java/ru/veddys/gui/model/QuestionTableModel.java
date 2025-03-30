package ru.veddys.gui.model;


import ru.veddys.domain.model.OpenQuestionCard;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class QuestionTableModel extends AbstractTableModel {
    private final String[] columnNames = new String[]{"ID", "Заголовок"};
    private final List<OpenQuestionCard> questions;

    public QuestionTableModel(List<OpenQuestionCard> questions) {
        this.questions = questions;
    }

    @Override
    public int getRowCount() {
        return questions.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return questions.get(rowIndex).getId();
        } else {
            return questions.get(rowIndex).getTitle();
        }
    }
}