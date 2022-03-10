package service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.WeakHashMap;

public class LoadFromCatalog {

    public WeakHashMap<String[], BufferedImage> getPersonalData() {
        return personalData;
    }

    private WeakHashMap<String [], BufferedImage> personalData = new WeakHashMap<>();

    LoadFromCatalog(){

    }
    private void loadFiles(Path catalogPath){

        try {

            personalData.put(loadTextFile(Path.of(catalogPath.toString()+"\\record.txt")),
                    loadImageFile(catalogPath + "\\image.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String [] loadTextFile(Path textPath) throws IOException {
        InputStream inputStream = Files.newInputStream(textPath);
        String personalInformation = Arrays.toString(inputStream.readAllBytes());

        return personalInformation.split(",");
    }

    private BufferedImage loadImageFile(String imagePath) throws IOException {
        return ImageIO.read(new File(imagePath));
    }

    public static void main(String[] args) {
        LoadFromCatalog loadFromCatalog = new LoadFromCatalog();
        loadFromCatalog
                .loadFiles(
                        Path.of("C:\\Pwr\\3 rok\\6 semestr\\ZT - Java\\dglazer_252743_java\\lab02\\source\\InformationPersonalReview\\out\\Test\\252743"));
    }
}
