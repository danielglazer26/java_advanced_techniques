package glazer.daniel.library.keygenerator;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static glazer.daniel.library.FileService.writeToFile;

public class GenerateKeys {

    public static void generateRSAKey(String publicKey, String privateKey) {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            createRSAKeys(keyGen, publicKey, privateKey);
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
        GenerateKeys.generateRSAKey("publicKey", "privateKey");
    }
}
