package glazer.daniel.pwr.app.gui;


import glazer.daniel.pwr.app.api.AnalysisException;
import glazer.daniel.pwr.app.api.AnalysisService;
import glazer.daniel.pwr.app.api.ClusteringException;
import glazer.daniel.pwr.app.api.DataSet;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class MainWindow extends JFrame {

    private DefaultListModel<String> listModelX, listModelY, listModelResults;
    private ServiceLoader<AnalysisService> loader;
    private DataSet dataContainer;
    private JPanel panel1;
    private JList<String> listX;
    private JList<String> listY;
    private JPanel listPanel;
    private JButton loadFileButton;
    private JLabel label1;
    private JLabel label2;
    private JTextField optionsField;
    private JButton algorithmStartButton;
    private JList<String> list1;
    private String pathToFile;

    public MainWindow() {
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        dataContainer = new DataSet();

        loadFileButton.addActionListener(e -> {
            loader = ServiceLoader.load(AnalysisService.class);
            pathToFile = actionChooseFile();
            loadDataFromFile();
        });

        algorithmStartButton.addActionListener(e -> {
            AnalysisService as = getAnalysisService();
            if (as != null)
                getDataFromService(as);
            pack();
        });

        list1.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                pack();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        pack();

    }

    private void getDataFromService(AnalysisService as) {
        try {
            checkOptions(as);
            as.submit(dataContainer);

            AtomicReference<DataSet> dataSet = new AtomicReference<>(new DataSet());
            new Thread(() -> {
                while (true) {
                    try {
                        DataSet ds = as.retrieve(true);
                        if (ds != null) {
                            dataSet.set(ds);
                            setListResults(dataSet.get().getData());
                            break;
                        }
                        Thread.sleep(100);
                    } catch (ClusteringException | InterruptedException ex) {

                    }
                }
            }).start();

        } catch (AnalysisException | NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private void checkOptions(AnalysisService as) throws AnalysisException {
        String opt = optionsField.getText();
        if (opt.length() > 0) {
            as.setOptions(opt.split(","));
        } else {
            as.setOptions(new String[]{});
        }
    }

    private AnalysisService getAnalysisService() {
        AnalysisService as = null;
        Iterator<AnalysisService> it = loader.iterator();
        for (int i = 0; i <= listY.getSelectedIndex(); i++) {
            as = it.next();
        }
        return as;
    }

    private void setLists(String[][] data) {
        listModelX.clear();
        listModelY.clear();

        Arrays.stream(data).forEach(strings -> listModelX.addElement(strings[0] + " " + strings[1]));
        loader.forEach(analysisService -> listModelY.addElement(analysisService.getName()));
        pack();
    }

    private void setListResults(String[][] data) {
        listModelResults.clear();
        Arrays.stream(data).forEach(strings -> Arrays.stream(strings).forEach(s -> listModelResults.addElement(s)));
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
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < headers.length - 1; i++) {
            stringBuilder.append(headers[i]).append(" ");
        }
        stringBuilder.append(headers[headers.length - 1]);
        label1.setText(stringBuilder.toString());
    }

    private void createUIComponents() {
        listModelX = new DefaultListModel<>();
        listModelY = new DefaultListModel<>();
        listModelResults = new DefaultListModel<>();
        listX = new JList<>(listModelX);
        listY = new JList<>(listModelY);
        list1 = new JList<>(listModelResults);
    }
}
