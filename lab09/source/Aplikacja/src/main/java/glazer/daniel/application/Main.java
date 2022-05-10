package glazer.daniel.application;

public class Main {

    public static void main(String... args) {
        System.setSecurityManager(new SecurityManager());
        System.setProperty("java.security.policy", "C:\\Pwr\\3 rok\\6 semestr" +
                "\\ZT - Java\\dglazer_252743_java\\lab09\\source\\" +
                "Aplikacja\\daniel.policy.generated");

        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
}
