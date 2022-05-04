package glazer.daniel.library.rsa;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import static glazer.daniel.library.FileService.loadFileBytes;
import static glazer.daniel.library.FileService.writeToFile;

public class EncoderRSA {

    private final Cipher cipher;
    public PrivateKey privateKey = null;

    public EncoderRSA() throws NoSuchAlgorithmException, NoSuchPaddingException {
        cipher = Cipher.getInstance("RSA");
    }

    public void getPrivate(String privateKeyFile) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(privateKeyFile).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        privateKey = kf.generatePrivate(spec);
    }

    public void encryptFile(String encryptedFile, Path pathToFileToEncrypt)
            throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        writeToFile(encryptedFile, cipher.doFinal(loadFileBytes(pathToFileToEncrypt)));
    }

    public static void main(String[] args) throws Exception {
        EncoderRSA encoderRSA = new EncoderRSA();
        encoderRSA.getPrivate("privateKey");
        if (encoderRSA.privateKey != null) {
            encoderRSA.encryptFile("C:\\Pwr\\3 rok\\6 semestr\\ZT - " +
                    "Java\\dglazer_252743_java\\lab09\\source\\Library\\noNieWiem.txt", Path.of("C:\\Pwr\\3 rok\\6 " +
                    "semestr\\ZT - " +
                    "Java\\dglazer_252743_java\\lab09\\source\\Library\\test.txt"));
        }
    }


}
