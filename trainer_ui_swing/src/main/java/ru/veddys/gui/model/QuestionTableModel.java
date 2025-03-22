package ru.veddys.gui.model;

import ru.veddys.domain.model.OpenQuestionCard;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class QuestionTableModel extends AbstractTableModel {

    private final String[] columns = new String[]{"ID", "Вопрос", "Ответ"};
    private final List<OpenQuestionCard> cards;

    public QuestionTableModel(List<OpenQuestionCard> cards) {
        this.cards = cards;
    }

    @Override
    public int getRowCount() {
        return cards.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int col) {
        return columns[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int colIndex) {
        switch (colIndex) {
            case 0: return cards.get(rowIndex).getId();
            case 1: return cards.get(rowIndex).getQuestion();
            case 2: return cards.get(rowIndex).getExpectedAnswer();
        };
        throw new IllegalArgumentException("Номер колонки больше возможного или отрицательный");
    }
}
