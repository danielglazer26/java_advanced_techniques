package glazer.daniel.library;

import javax.crypto.SecretKey;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService {

    public static void writeToFile(String fileName, byte[] key) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(key);
        fos.close();
    }

    public static void writeToFile(String fileName, SecretKey key) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(key);
        oos.close();
        fos.close();
    }

    public static byte[] loadFileBytes(Path filePath) throws IOException {
        return Files.readAllBytes(filePath);
    }

    public static SecretKey loadFileObject(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        SecretKey secretKey = (SecretKey) ois.readObject();
        ois.close();
        fis.close();
        return secretKey;
    }
}
