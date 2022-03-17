package gui;

import service.DataFromFile;
import service.LoadFromCatalog;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

public class Application extends JDialog {
    private JPanel contentPane;
    private JList<Object> list1;
    private JLabel labelSave;
    private JButton button1;


    public Application() {
        setContentPane(contentPane);
        setModal(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        Path path = Path.of("C:\\Pwr\\3 rok\\6 semestr\\ZT - " +
                "Java\\dglazer_252743_java\\lab02\\source\\InformationPersonalReview\\out\\Test\\");

        LoadFromCatalog loadFromCatalog = new LoadFromCatalog(path);
        catalogListCreation(path);


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelSave.setText(loadFromCatalog.loadCatalogs(list1.getSelectedValue().toString()));
            }
        });


    }

    private void catalogListCreation(Path catalogsPath) {
        try {
            List<String> catalogList = makeListOfCatalogs(catalogsPath);
            list1.setListData(catalogList.toArray());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        Application dialog = new Application();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private static List<String> makeListOfCatalogs(Path catalogPath) throws IOException {
        return Files.list(catalogPath)
                .filter(Files::isDirectory)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
    }
}
