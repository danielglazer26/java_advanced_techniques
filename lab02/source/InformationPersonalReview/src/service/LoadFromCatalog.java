package service;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
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

    public String loadCatalogs(String catalogName) {


        if (!personalData.containsKey(catalogName)) {
            personalData.put(new String(catalogName), loadDataFromCatalog(catalogPath + "\\" + catalogName));
            return "Wczytano z dysku";
        } else
            return "Wczytano z mapy";

    }

    /*private boolean checkKey(String catalogNameWeak) {

        for (Map.Entry<WeakReference<String>, DataFromFile> entry : personalData.entrySet()) {
            WeakReference<String> stringWeakReference = entry.getKey();
            if (Objects.equals(stringWeakReference.get(), catalogNameWeak))
                return false;
        }
        return true;
    }*/

    private DataFromFile loadDataFromCatalog(String pathToCatalog) {
        try {
            String[] tableData = loadTextFile(pathToCatalog);
            ImageIcon icon = new ImageIcon(pathToCatalog + "\\image.png");
            return new DataFromFile(tableData, icon);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private String[] loadTextFile(String pathToCatalog) throws IOException {
        InputStream inputStream = Files.newInputStream(Path.of(pathToCatalog + "\\record.txt"));
        String personalInformation = new String(inputStream.readAllBytes());

        return personalInformation.split(",");
    }

    private BufferedImage loadImageFile(String imagePath) throws IOException {
        return ImageIO.read(new File(imagePath));
    }


}
