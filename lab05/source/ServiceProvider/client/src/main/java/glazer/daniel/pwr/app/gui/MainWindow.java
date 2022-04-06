package glazer.daniel.pwr.app.gui;


import glazer.daniel.pwr.app.api.AnalysisException;
import glazer.daniel.pwr.app.api.AnalysisService;
import glazer.daniel.pwr.app.api.ClusteringException;
import glazer.daniel.pwr.app.api.DataSet;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ServiceLoader;


public class MainWindow extends JFrame {

    private DefaultListModel<String> listModelX;
    private DefaultListModel<String> listModelY;
    private ServiceLoader<AnalysisService> loader;
    private DataSet dataContainer = null;
    private JPanel panel1;
    private JList<String> listX;
    private JList<String> listY;
    private JPanel listPanel;
    private JButton loadFileButton;
    private JLabel label1;
    private JLabel label2;
    private JTextField optionsField;
    private JButton algorithmStartButton;
    private String pathToFile;

    public MainWindow() {
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        loadFileButton.addActionListener(e -> {
            loader = ServiceLoader.load(AnalysisService.class);
            pathToFile = actionChooseFile();
            loadDataFromFile();
            loader.stream().forEach(analysisServiceProvider -> {

                if (analysisServiceProvider.get().getName().equals("This class calculate Mediana")) {
                    try {
                        System.out.println(analysisServiceProvider.get().getName());
                        var a = analysisServiceProvider.get();
                        a.submit(dataContainer);
                        Arrays.stream(a.retrieve(true).getData()).forEach(strings -> Arrays.stream(strings).forEach(System.out::println));
                    } catch (AnalysisException | ClusteringException ex) {

                    }
                }
            });


        });

        algorithmStartButton.addActionListener(e -> {
            AnalysisService as = null;
            var it = loader.iterator();
            for (int i = 0; i <= listY.getSelectedIndex(); i++) {
                as = it.next();
            }

            try {
                as.submit(dataContainer);

                String opt = optionsField.getText();
                if (opt.length() > 0) {
                    as.setOptions(opt.split(","));
                }
               else {
                    as.setOptions(new String[]{});
                }

               DataSet dataSet = as.retrieve(true);
                setLists(dataSet.getData());
            } catch (AnalysisException | NullPointerException | ClusteringException ex) {
                ex.printStackTrace();
            }
        });
        pack();


        dataContainer = new DataSet();
    }

    private void setLists(String[][] data) {
        listModelX.clear();
        listModelY.clear();

        Arrays.stream(data).forEach(strings -> listModelX.addElement(strings[0] + " " + strings[1]));
        loader.forEach(analysisService -> listModelY.addElement(analysisService.getName()));
        pack();
    }

    /**
     * Starts a fileChooser dialog
     *
     * @return directory to folder with classes
     */
    private String actionChooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSystemView(FileSystemView.getFileSystemView());
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (fileChooser.showOpenDialog(null) == 0) {
            return fileChooser.getSelectedFile().toString();
        }

        return null;
    }

    private void loadDataFromFile() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(pathToFile));

            String[] headers = br.readLine().split("\t");
            dataContainer.setHeader(headers);

            ArrayList<String[]> arrayList = new ArrayList<>();

            br.lines().forEach(s -> arrayList.add(s.split("\t")));
            String[][] data = arrayList.toArray(new String[0][0]);

            setLists(data);
            setListsLabels(headers);

            dataContainer.setData(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(br).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setListsLabels(String[] headers) {
        label1.setText(headers[0] + " " + headers[1]);
        label2.setText(headers[1]);
    }

    private void createUIComponents() {
        listModelX = new DefaultListModel<>();
        listModelY = new DefaultListModel<>();
        listX = new JList<>(listModelX);
        listY = new JList<>(listModelY);
    }
}
