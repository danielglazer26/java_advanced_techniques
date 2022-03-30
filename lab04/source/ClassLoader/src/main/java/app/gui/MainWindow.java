package app.gui;

import app.loader.CustomClassLoader;
import app.loader.interfaces.ClassMethods;
import app.status.MyStatusListener;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainWindow extends JFrame implements ClassMethods {
    private DefaultListModel<String> listModelToUnload;
    private DefaultListModel<String> listModelToLoad;
    private DefaultListModel<String> listInfoModel;
    private JButton loadClassButton;
    private JButton unloadClassButton;
    private JList listToUnload;
    private JList listToLoad;
    private JButton addTaskButton;
    private JPanel contentPane;
    private List<Path> pathList;
    private JList listInfo;
    private JTextField taskText;
    private CustomClassLoader classLoader;
    private String path;

    public MainWindow() {
        setContentPane(contentPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        classLoader = new CustomClassLoader(Paths.get(path));

        loadButtonAction();
        unloadButtonAction();
        addTaskButtonAction();

        pack();
    }

    private String actionChooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSystemView(FileSystemView.getFileSystemView());
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (fileChooser.showOpenDialog(null) == 0) {
            return fileChooser.getSelectedFile().toString();
        }

        return null;
    }

    private void addTaskButtonAction() {
        addTaskButton.addActionListener(e -> {
            if (taskText.getText().length() > 0) {
                Class<?> myClass = classLoader.getListClass().get(listToLoad.getSelectedIndex());
                try {
                    Object o = ClassMethods.createObjectFromConstructor(myClass);

                    Method submitTask = ClassMethods.createMethodSubmitTask(myClass);
                    Method getResult = ClassMethods.createMethodGetResult(myClass);

                    MyStatusListener myStatusListener = new MyStatusListener((String) listToLoad.getSelectedValue());

                    submitTask.invoke(o, taskText.getText(), myStatusListener);


                    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

                    executor.scheduleAtFixedRate(() -> {
                        String result = null;
                        try {
                            result = (String) getResult.invoke(o);
                        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
                            ex.printStackTrace();
                        }
                        if (result != null) {
                            myStatusListener.setResultLabel(result);
                            executor.shutdown();
                        }

                    }, 1, 500, TimeUnit.MILLISECONDS);

                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    private void unloadButtonAction() {
        unloadClassButton.addActionListener(e -> {
            listModelToUnload.addElement((String) listToLoad.getSelectedValue());
            listInfoModel.clear();
            listModelToLoad.remove(listToLoad.getSelectedIndex());

            classLoader = null;
            System.gc();

            classLoader = new CustomClassLoader(Paths.get(path));
            if (!listModelToLoad.isEmpty())
                Arrays.stream(listModelToLoad.toArray()).forEach(l -> loadClassByName((String) l));
            else
                refreshClass();
        });
    }

    private void loadButtonAction() {
        loadClassButton.addActionListener(e -> {
            String classToLoad = (String) listToUnload.getSelectedValue();

            listModelToLoad.addElement(classToLoad);

            loadClassByName(classToLoad);

            listModelToUnload.remove(listToUnload.getSelectedIndex());

            pack();
        });
    }

    private void loadClassByName(String classToLoad) {

        AtomicReference<String> packages = new AtomicReference<>("");
        pathList.forEach(e -> {
            if (e.getFileName().toString().equals(classToLoad)) {
                packages.set(e.toString());
                packages.set(packages.get().replace(path + "\\", ""));
                packages.set(packages.get().replace(File.separatorChar, '.'));
                packages.set(packages.get().replace(".class", ""));

            }
        });

        Class<?> myClass = classLoader.findClass(packages.get());
        classLoader.addListClass(myClass);

        try {
            Object o = ClassMethods.createObjectFromConstructor(myClass);
            Method method = ClassMethods.createMethodGetInfo(myClass);
            listInfoModel.addElement((String) method.invoke(o));

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private List<Path> loadFileClass() throws IOException {
        try (Stream<Path> pathStream = Files.walk(Paths.get(path))) {
            return pathStream.filter(Files::isRegularFile)
                    //.filter(s -> s.toString().endsWith("Processor.class"))
                    .filter(s -> s.toString().endsWith(".class"))
                    .collect(Collectors.toList());
        }
    }

    private void createUIComponents() {
        path = actionChooseFile();

        listModelToUnload = new DefaultListModel<>();
        listToUnload = new JList<>(listModelToUnload);

        refreshClass();

        listModelToLoad = new DefaultListModel<>();
        listToLoad = new JList<>(listModelToLoad);

        listInfoModel = new DefaultListModel<>();
        listInfo = new JList<>(listInfoModel);
    }

    private void refreshClass() {
        try {
            pathList = loadFileClass();
            listModelToUnload.clear();
            for (Path classFile : pathList) {
                listModelToUnload.addElement(classFile.getFileName().toString());
            }
            pack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
