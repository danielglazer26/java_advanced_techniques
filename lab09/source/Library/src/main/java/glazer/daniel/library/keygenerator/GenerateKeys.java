package glazer.daniel.library.keygenerator;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static glazer.daniel.library.FileService.writeToFile;

public class GenerateKeys {

    public static void generateAsymmetricKey(String publicKey, String privateKey, int size, String algorithm) {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
            keyGen.initialize(size);
            createRSAKeys(keyGen, publicKey, privateKey);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateSymmetricKey(String fileName, int size, String algorithm) {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(algorithm);
            keyGenerator.init(size);
            SecretKey secretKey = keyGenerator.generateKey();
            writeToFile(fileName, secretKey);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void createRSAKeys(KeyPairGenerator keyGen, String publicKey, String privateKey) throws IOException {
        KeyPair pair = keyGen.generateKeyPair();
        writeToFile(publicKey, pair.getPublic().getEncoded());
        writeToFile(privateKey, pair.getPrivate().getEncoded());
    }


    public static void main(String[] args) {
        //GenerateKeys.generateAsymmetricKey("publicKey", "privateKey", 1024, "RSA");
        GenerateKeys.generateSymmetricKey("privateKey", 256, "AES");
    }
}
