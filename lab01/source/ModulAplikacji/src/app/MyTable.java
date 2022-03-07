package app;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class MyTable extends AbstractTableModel {

    private ArrayList<ArrayList<String>> listOfFiles = new ArrayList<>();
    private final String[] columnNames;

    MyTable() {
        columnNames = new String[]{"Nazwa pliku", "Zmiana"};
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public int getRowCount() {
        return listOfFiles.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) return listOfFiles.get(rowIndex).get(columnIndex);
        if (columnIndex == 1) return listOfFiles.get(rowIndex).get(columnIndex);
        return null;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int c) { return getValueAt(0, c).getClass();}

    public void setListOfFiles(ArrayList<ArrayList<String>> listOfFiles) {
        this.listOfFiles = listOfFiles;
    }

}

