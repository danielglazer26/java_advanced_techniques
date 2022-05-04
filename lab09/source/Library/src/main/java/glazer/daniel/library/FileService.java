package glazer.daniel.library;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService {

    public static void writeToFile(String fileName, byte[] key) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(key);
        fos.close();
    }

    public static byte[] loadFileBytes(Path filePath) throws IOException {
        return Files.readAllBytes(filePath);
    }
}
