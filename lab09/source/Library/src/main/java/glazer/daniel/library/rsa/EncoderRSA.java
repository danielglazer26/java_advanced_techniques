package glazer.daniel.library.rsa;

import glazer.daniel.library.FileService;

import javax.crypto.*;
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

    private Cipher cipher;
    private PrivateKey privateKey = null;
    private SecretKey secretKey = null;

    public void setCipher(String algorithm) {
        try {
            this.cipher = Cipher.getInstance(algorithm);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public void getPrivate(String privateKeyFile, String algorithm) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(privateKeyFile).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        privateKey = kf.generatePrivate(spec);
    }

    public void getPrivateSymmetric(String privateKeyFile) throws Exception {
        secretKey = FileService.loadFileObject(privateKeyFile);
    }

    public void encryptFile(String encryptedFile, Path pathToFileToEncrypt)
            throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        writeToFile(encryptedFile, cipher.doFinal(loadFileBytes(pathToFileToEncrypt)));
    }

    public void encryptFileSymmetric(String encryptedFile, Path pathToFileToEncrypt)
            throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        writeToFile(encryptedFile, cipher.doFinal(loadFileBytes(pathToFileToEncrypt)));
    }

    public static void main(String[] args) throws Exception {

        EncoderRSA encoderRSA = new EncoderRSA();
        encoderRSA.setCipher("AES");
        encoderRSA.getPrivateSymmetric(args[0]);
        encoderRSA.encryptFileSymmetric(args[1], Path.of(args[2]));

        /*encoderRSA.getPrivate(args[0], "RSA");
        if (encoderRSA.privateKey != null) {
            encoderRSA.encryptFile(args[1], Path.of(args[2]));
        }*/
    }


}
