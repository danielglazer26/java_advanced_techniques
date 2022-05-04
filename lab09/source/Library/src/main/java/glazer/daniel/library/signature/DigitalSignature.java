package glazer.daniel.library.signature;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;

public class DigitalSignature {
    public static void main(String[] args) {
        KeyPairGenerator keyPairGenerator;
        SecureRandom random;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("DSA", "SUN");
            random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyPairGenerator.initialize(1024, random);
            KeyPair pair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();

            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
            dsa.initSign(privateKey);

            FileInputStream fis = new FileInputStream("test.txt");
            BufferedInputStream bufin = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufin.read(buffer)) >= 0) {
                dsa.update(buffer, 0, len);
            };
            bufin.close();

            byte[] realSig = dsa.sign();

            FileOutputStream sigfos = new FileOutputStream("test2.txt");
            sigfos.write(realSig);
            sigfos.close();


            byte[] key = publicKey.getEncoded();
            FileOutputStream keyfos = new FileOutputStream("danielpk");
            keyfos.write(key);
            keyfos.close();

        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | IOException | SignatureException e) {
            e.printStackTrace();
        }
    }
}
