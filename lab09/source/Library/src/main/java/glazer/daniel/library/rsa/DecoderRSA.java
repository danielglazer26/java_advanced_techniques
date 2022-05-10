package glazer.daniel.library.rsa;

import glazer.daniel.library.FileService;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import static glazer.daniel.library.FileService.writeToFile;

public class DecoderRSA {

    private Cipher cipher;
    private PublicKey publicKey;
    private SecretKey secretKey;

    public void setCipher(String algorithm) {
        try {
            this.cipher = Cipher.getInstance(algorithm);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public void getPublic(Path publicKeyPath, String algorithm) throws Exception {
        byte[] keyBytes = Files.readAllBytes(publicKeyPath);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        publicKey = kf.generatePublic(spec);
    }

    public void getPublicSymmetric(String publicKeyPath) throws Exception {
        secretKey = FileService.loadFileObject(publicKeyPath);
    }

    public void decryptFile(String decryptedFile, Path pathToFileToDecrypt)
            throws IOException, GeneralSecurityException {
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        writeToFile(decryptedFile, cipher.doFinal(FileService.loadFileBytes(pathToFileToDecrypt)));
    }

    public void decryptFileSymmetric(String decryptedFile, Path pathToFileToDecrypt)
            throws IOException, GeneralSecurityException {
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        writeToFile(decryptedFile, cipher.doFinal(FileService.loadFileBytes(pathToFileToDecrypt)));
    }

    public static void main(String[] args) throws Exception {
        DecoderRSA decoderRSA = new DecoderRSA();
        decoderRSA.setCipher("AES");
        decoderRSA.getPublicSymmetric(args[0]);
        decoderRSA.decryptFileSymmetric(args[1], Path.of(args[2]));
    }
}
