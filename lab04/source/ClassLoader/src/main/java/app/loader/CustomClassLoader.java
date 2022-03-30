package app.loader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CustomClassLoader extends ClassLoader {

    private final Path pathToClasses;
    private final ArrayList<Class<?>> listClass = new ArrayList<>();

    public CustomClassLoader(Path pathToClasses) {
        this.pathToClasses = pathToClasses;
    }

    public Class<?> findClass(String classFile) {
        Path classFilePath =
                Paths.get(pathToClasses + File.separator + classFile.replace('.', File.separatorChar) + ".class");

        byte[] fileBytes;
        try {
            fileBytes = Files.readAllBytes(classFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return defineClass(classFile, fileBytes, 0, fileBytes.length);
    }

    public ArrayList<Class<?>> getListClass() {
        return listClass;
    }

    public void addListClass(Class<?> listClass) {
        this.listClass.add(listClass);
    }
}
