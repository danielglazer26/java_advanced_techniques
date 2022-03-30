package app.gui;

import app.loader.CustomClassLoader;
import app.loader.interfaces.ClassMethods;
import app.status.MyStatusListener;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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
    private JList listInfo;
    private List<Path> classFiles;
    private CustomClassLoader classLoader;
    private final String path = "C:\\Pwr\\3 rok\\6 semestr\\ZT - " +
            "Java\\dglazer_252743_java\\lab04\\source\\Processors\\out\\production\\Processors\\";

    public MainWindow() {
        setContentPane(contentPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        classLoader = new CustomClassLoader(Paths.get(path));

        loadButtonAction();
        unloadButtonAction();
        addTaskButtonAction();

        pack();
    }

    private void addTaskButtonAction() {
        addTaskButton.addActionListener(e -> {
            Class <?> myClass = classLoader.getListClass().get(listToLoad.getSelectedIndex());
            try {
                Object o = ClassMethods.createObjectFromConstructor(myClass);

                Method submitTask = ClassMethods.createMethodSubmitTask(myClass);
                MyStatusListener myStatusListener = new MyStatusListener((String) listToLoad.getSelectedValue());
                submitTask.invoke(o, "Tekst na wejście", myStatusListener);

                Method getResult = ClassMethods.createMethodGetResult(myClass);

                ExecutorService executor = Executors.newSingleThreadExecutor();

                executor.submit(() -> {
                    String result = null;
                    while (true) {
                        try {
                            Thread.sleep(800);

                          result = (String) getResult.invoke(o);
                        } catch (InterruptedException | IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
                            ex.printStackTrace();
                        }
                        if (result != null) {
                            myStatusListener.setResultLabel(result);
                            executor.shutdown();
                            break;
                        }
                    }
                });

            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
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

        Class<?> myClass =
                classLoader.findClass("app.processors." + classToLoad.replace(".class", ""));
        classLoader.addListClass(myClass);

        try {
            Object o = ClassMethods.createObjectFromConstructor(myClass);
            Method method = ClassMethods.createMethodGetInfo(myClass);
            listInfoModel.addElement((String)method.invoke(o));

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private List<Path> loadFileClass() throws IOException {
        return Files.list(Paths.get(path + "app\\processors\\"))
                .filter(Files::isRegularFile)
                .filter(s -> s.toString().endsWith("class"))
                .collect(Collectors.toList());
    }

    private void createUIComponents() {
        listModelToUnload = new DefaultListModel<>();
        listToUnload = new JList<>(listModelToUnload);
        try {
            classFiles = loadFileClass();
            for (Path classFile : classFiles) {
                listModelToUnload.addElement(classFile.getFileName().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        listModelToLoad = new DefaultListModel<>();
        listToLoad = new JList<>(listModelToLoad);

        listInfoModel = new DefaultListModel<>();
        listInfo = new JList<>(listInfoModel);
    }
}
