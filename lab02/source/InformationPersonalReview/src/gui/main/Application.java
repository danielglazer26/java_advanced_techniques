package gui.main;

import gui.viewer.ContentOne;
import gui.viewer.ContentViewerFrame;
import service.data.DataFromFile;
import service.loader.LoadFromCatalog;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Application extends JFrame {
    private JPanel contentPane;
    private JList<Object> listViewOfCatalogs;
    private JLabel labelSave;
    private JButton viewerChangeButton;
    private final LoadFromCatalog loadFromCatalog;
    private DataFromFile data = null;
    private ContentViewerFrame contentViewerFrame;
    private Boolean changeViewer = true;

    public static void main(String[] args) {
        String path;
        if (args.length == 0)
             path = actionChooseFile();
        else
            path = args[0];
        Application dialog = new Application(path);
        dialog.pack();
        dialog.setVisible(true);
    }

    private static String actionChooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSystemView(FileSystemView.getFileSystemView());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (fileChooser.showOpenDialog(null) == 0) {
            return fileChooser.getSelectedFile().toString();
        }
        return actionChooseFile();
    }


    public Application(String pathS) {

        windowSettings();

        Path path = Path.of(pathS);
        loadFromCatalog = new LoadFromCatalog(path);
        catalogListCreation(path);

        createContentViewer(new ContentOne());

        listViewListenerCreation();
        buttonListenerCreation();

    }

    private void windowSettings() {
        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }

    private void catalogListCreation(Path catalogsPath) {
        try {
            List<String> catalogList = makeListOfCatalogs(catalogsPath);
            listViewOfCatalogs.setListData(catalogList.toArray());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> makeListOfCatalogs(Path catalogPath) throws IOException {
        return Files.list(catalogPath)
                .filter(Files::isDirectory)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
    }

    private void createContentViewer(ContentViewerFrame contentViewer) {
        contentViewerFrame = contentViewer;
        contentViewer.pack();
        contentViewer.setVisible(true);
    }

    private void listViewListenerCreation() {
        listViewOfCatalogs.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                data = loadFromCatalog.loadCatalogs(listViewOfCatalogs.getSelectedValue().toString(), labelSave);
                contentViewerFrame.setData(data);
            }
        });
    }

    private void buttonListenerCreation() {
        viewerChangeButton.addActionListener(e -> {
            contentViewerFrame.dispose();
            if (changeViewer)
                createContentViewer(new ContentViewerFrame());

            else
                createContentViewer(new ContentOne());
            contentViewerFrame.setData(data);
            changeViewer = !changeViewer;
        });
    }


    private void onCancel() {
        if (contentViewerFrame.isVisible())
            contentViewerFrame.dispose();
        dispose();
    }
}
