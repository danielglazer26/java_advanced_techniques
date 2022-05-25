package application;

import javax.script.ScriptException;
import javax.swing.*;

public class MainWindow extends JFrame {
    private int current_turn;
    private Timer timer;

    private DrawMap map;
    private JPanel contentPane;
    private JPanel mapPanel;
    private JButton startButton;
    private JButton loadScript;
    int map_size = 10;
    int window_resizable = 50;

    private Engine engine;

    public MainWindow() {
        setContentPane(contentPane);

        loadScript.addActionListener(e -> {
            engine = new Engine();
            try {
                String s = "C:\\Pwr\\3 rok\\6 semestr\\ZT - " +
                        "Java\\dglazer_252743_java\\lab12\\source\\CellularAutomaton\\src" +
                        "\\main\\resources\\LangtonAnt.js";
                engine.loadScript(/*actionChooseFile()*/s);
            } catch (Exception ignore) {
            }
        });

        startButton.addActionListener(e -> {
            current_turn = 0;
            timer = new Timer(100, ex -> {
                current_turn++;
                if (current_turn < 1000) {
                    timer.start();
                    try {
                        map.setMap(engine.iterationAnt());
                        repaint();
                    } catch (ScriptException | NoSuchMethodException exc) {
                        throw new RuntimeException(exc);
                    }

                } else {
                    timer.stop();
                }
                repaint();

            });

            timer.start();

        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void createUIComponents() {
        map = new DrawMap(map_size, window_resizable, null);
        mapPanel = map;
    }

    private String actionChooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (fileChooser.showOpenDialog(this) == 0) {
            return fileChooser.getSelectedFile().toString();
        }
        return null;
    }

    public static void main(String[] args) {
        MainWindow dialog = new MainWindow();
        dialog.pack();
        dialog.setVisible(true);
    }

}
