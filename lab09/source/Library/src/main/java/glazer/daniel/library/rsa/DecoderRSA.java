package glazer.daniel.library.rsa;

import glazer.daniel.library.FileService;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
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

    private final Cipher cipher;
    private PublicKey publicKey;

    public DecoderRSA() throws NoSuchAlgorithmException, NoSuchPaddingException {
        cipher = Cipher.getInstance("RSA");
    }

    public void getPublic(Path publicKeyPath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(publicKeyPath);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        publicKey = kf.generatePublic(spec);
    }

    public void decryptFile(String decryptedFile, Path pathToFileToDecrypt)
            throws IOException, GeneralSecurityException {
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        writeToFile(decryptedFile, cipher.doFinal(FileService.loadFileBytes(pathToFileToDecrypt)));
    }

    public static void main(String[] args) throws Exception {
        DecoderRSA decoderRSA = new DecoderRSA();
        decoderRSA.getPublic(Path.of("C:\\Pwr\\3 rok\\6 semestr\\ZT - Java\\dglazer_252743_java\\lab09\\source\\Library\\publicKey"));
        if(decoderRSA.publicKey != null){
            decoderRSA.decryptFile("C:\\Pwr\\3 rok\\6 semestr\\ZT - " +
                    "Java\\dglazer_252743_java\\lab09\\source\\Library\\toJestPlikRozszyfrowany.txt", Path.of("C:\\Pwr\\3 rok\\6 semestr\\ZT - " +
                    "Java\\dglazer_252743_java\\lab09\\source\\Library\\noNieWiem.txt"));
        }
    }
}
