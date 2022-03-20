package service.loader;

import service.data.DataFromFile;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.WeakHashMap;

public class LoadFromCatalog {

    private final Path catalogPath;
    private WeakHashMap<String, DataFromFile> personalData = new WeakHashMap<>();

    public LoadFromCatalog(Path path) {
        catalogPath = path;
    }

    public DataFromFile loadCatalogs(String catalogName, JLabel labelSave) {

        if (!personalData.containsKey(catalogName)) {
            String pathToFiles = catalogPath + "\\" + catalogName;
            try {
                personalData.put(new String(catalogName),
                        new DataFromFile(getStrings(pathToFiles), getImageIcon(pathToFiles)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            labelSave.setText("Wczytano z dysku");
        } else
             labelSave.setText("Wczytano z mapy");

        return personalData.get(catalogName);
    }


    private ImageIcon getImageIcon(String pathToCatalog) {
        ImageIcon icon = new ImageIcon(pathToCatalog + "\\image.jpg");
        return icon;
    }

    private String[] getStrings(String pathToCatalog) throws IOException {
        String[] tableData = loadTextFile(pathToCatalog);
        return tableData;
    }

    private String[] loadTextFile(String pathToCatalog) throws IOException {
        InputStream inputStream = Files.newInputStream(Path.of(pathToCatalog + "\\record.txt"));
        String personalInformation = new String(inputStream.readAllBytes());
        inputStream.close();

        return personalInformation.split(",");
    }

}
