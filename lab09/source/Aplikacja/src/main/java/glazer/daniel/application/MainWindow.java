package glazer.daniel.application;

import glazer.daniel.library.keygenerator.GenerateKeys;
import glazer.daniel.library.rsa.DecoderRSA;
import glazer.daniel.library.rsa.EncoderRSA;

import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public class MainWindow extends JFrame {

    private JButton rsaKey;
    private JTextField keysName;
    private JButton loadFileToEncrypted;
    private JButton loadFileToDecrypted;
    private JComboBox chooseAlgorithm;
    private JPanel contentPane;
    private JTextField encryptedFileName;
    private JTextField decryptedFileName;
    private EncoderRSA encoderRSA;
    private DecoderRSA decoderRSA;

    MainWindow() {
        setContentPane(contentPane);
        try {
            encoderRSA = new EncoderRSA();
            decoderRSA = new DecoderRSA();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        loadFileToEncrypted.addActionListener(e -> {
            try {
                encoderRSA.getPrivate(startFileChooser("Private key"));
                encoderRSA.encryptFile(encryptedFileName.getText(), Path.of(startFileChooser("File to encrypted")));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        loadFileToDecrypted.addActionListener(e -> {
            try {
                decoderRSA.getPublic(Path.of(startFileChooser("Public key")));
                decoderRSA.decryptFile(decryptedFileName.getText(), Path.of(startFileChooser("File to decrypted")));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        rsaKey.addActionListener(e -> {
            String[] keys = keysName.getText().split(";");
            GenerateKeys.generateRSAKey(keys[1], keys[0]);
        });

        pack();
    }

    public String startFileChooser(String title) {
        JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        fileChooser.setDialogTitle(title);
        fileChooser.setFileSystemView(FileSystemView.getFileSystemView());
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (fileChooser.showOpenDialog(null) == 0) {
            return fileChooser.getSelectedFile().toString();
        }
        return null;
    }
}


