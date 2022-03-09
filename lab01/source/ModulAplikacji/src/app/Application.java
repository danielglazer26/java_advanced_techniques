package app;

import library.Encoder;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.KeyEvent;


public class Application extends JDialog {
    private JPanel contentPane;
    private JButton buttonChooseFile;
    private JButton buttonRefresh;
    private JTable table1;
    private JLabel catalogLocalization;
    private JScrollPane scrollTable;
    private MyTable myTable;
    private String filePath = null;

    public Application() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonChooseFile);

        buttonChooseFile.addActionListener(e -> actionChooseFile());

        buttonRefresh.addActionListener(e -> actionRefresh());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    public static void main(String[] args) {
        Application dialog = new Application();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    /**
     * Panel wyboru katalogu
     */
    private void actionChooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (fileChooser.showOpenDialog(this) == 0) {

            filePath = fileChooser.getSelectedFile().toString();

            catalogLocalization.setText(filePath);
            Encoder.choosePath(filePath);

            myTable.setListOfFiles(Encoder.findChanges());

            resizeColumnWidth(table1);
            SwingUtilities.updateComponentTreeUI(this);

        }
    }

    private void actionRefresh() {
        refreshFiles();
    }

    /**
     * Utworzenie tabeli
     */
    private void createUIComponents() {
        scrollTable = new JScrollPane();
        add(scrollTable, BorderLayout.CENTER);

        myTable = new MyTable();
        table1 = new JTable(myTable);

        scrollTable.setViewportView(table1);
    }

    /**
     * Odświeżenie zmian w plikach
     */
    private void refreshFiles() {
        if (filePath != null) {
            catalogLocalization.setText(filePath);
            Encoder.choosePath(filePath);

            myTable.setListOfFiles(Encoder.findChanges());
            SwingUtilities.updateComponentTreeUI(this);
        }
    }

    /**
     * Dopasowywanie column w tabeli
     */
    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300)
                width = 300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }

    }
}
