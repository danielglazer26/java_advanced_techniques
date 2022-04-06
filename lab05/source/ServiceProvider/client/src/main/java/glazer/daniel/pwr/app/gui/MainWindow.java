package glazer.daniel.pwr.app.gui;


import glazer.daniel.pwr.app.api.AnalysisException;
import glazer.daniel.pwr.app.api.AnalysisService;
import glazer.daniel.pwr.app.api.ClusteringException;
import glazer.daniel.pwr.app.api.DataSet;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class MainWindow extends JFrame {

    private DefaultTableModel tableModel;
    private DefaultListModel<String>  listModelY, listModelResults;
    private ServiceLoader<AnalysisService> loader;
    private DataSet dataContainer;
    private JPanel panel1;
    private JList<String> listY;
    private JPanel listPanel;
    private JButton loadFileButton;
    private JLabel label1;
    private JLabel label2;
    private JTextField optionsField;
    private JButton algorithmStartButton;
    private JList<String> list1;
    private JTable table1;
    private String pathToFile;

    public MainWindow() {
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 100);
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

        setSize(700, 200);

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

        } catch (AnalysisException  ex) {
           JDialog d = new JDialog(this, "Exception");
           JLabel label = new JLabel(ex.getMessage());
           label.setHorizontalAlignment(SwingConstants.CENTER);
           d.add(label);
           d.setBounds(300, 300, 200, 100);
           d.setVisible(true);
        }catch (NullPointerException e){
            e.printStackTrace();
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

    private void setInterfaceData(String[][] data, String[] headers) {
        tableModel = new DefaultTableModel(data, headers){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(tableModel);

        listModelY.clear();
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

            setInterfaceData(data, headers);

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

    private void createUIComponents() {
        listModelY = new DefaultListModel<>();
        listModelResults = new DefaultListModel<>();
        listY = new JList<>(listModelY);
        list1 = new JList<>(listModelResults);
    }
}
