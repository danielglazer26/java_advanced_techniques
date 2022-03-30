package app.gui;


import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
}
