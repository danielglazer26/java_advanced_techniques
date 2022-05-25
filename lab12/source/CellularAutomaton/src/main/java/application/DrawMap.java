package application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DrawMap extends JPanel {

    private final int map_size;
    private final int window_resizable;
    private ArrayList<ArrayList<Double>> map;

    /**
     * Przypisuje wartosci do zmiennych prywatnych
     *
     * @param map_size         rozmiar mapy
     * @param window_resizable wspolczynnik skalowania okna
     * @param map
     */
    public DrawMap(int map_size, int window_resizable, ArrayList<ArrayList<Double>> map) {
        this.map_size = map_size;
        this.window_resizable = window_resizable;
        this.map = map;
    }

    /**
     * Wywoluje metody od rysowania mapy i legendy
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(((float) (window_resizable)) / 50, ((float) (window_resizable)) / 50);
        drawMap(g2d);
    }


    /**
     * Rysowanie mapy
     */
    private void drawMap(Graphics2D g2d) {

        if (map != null)
            for (int i = 0; i < map_size; i++) {
                for (int j = 0; j < map_size; j++) {
                    double mapValue = map.get(j).get(i);

                    if (mapValue >= 10) {
                        mapValue -= 10;
                        g2d.setPaint(Color.BLACK);
                        g2d.fillRect(10 + i * 50, 10 + j * 50, 50, 50);
                    } else {
                        g2d.setPaint(Color.WHITE);
                        g2d.fillRect(10 + i * 50, 10 + j * 50, 50, 50);
                    }
                    if (mapValue != 0) {
                        AffineTransform backup = g2d.getTransform();
                        makeRotation(mapValue, g2d, i, j);
                        g2d.drawImage(getImage("src/main/resources/ant.png", new Color(255, 0, 0)), 10 + i * 50, 10 + j * 50, null);
                        g2d.setTransform(backup);
                    }

                    g2d.setPaint(Color.BLACK);
                }

            }


    }

    public BufferedImage getImage(String filePath, Color color) {
        BufferedImage image = null;

        File imageFile = new File(filePath);
        try {
            image = ImageIO.read(imageFile);
            int rgb = color.getRGB();
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    if (image.getRGB(i, j) != 0)
                        image.setRGB(i, j, rgb);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void makeRotation(double direction, Graphics2D g2d, int i, int j) {
        AffineTransform a = null;
        switch ((int) direction) {
            case 1:
                a = AffineTransform.getRotateInstance(-70, 50 + i * 50, 30 + j * 50);
                break;
            case 2:
                a = AffineTransform.getRotateInstance(-40, 42 + i * 50, 38 + j * 50);
                break;
            case 3:
                a = AffineTransform.getRotateInstance(40, 40 + i * 50, 40 + j * 50);
                break;
            case 4:
                a = AffineTransform.getRotateInstance(70, 30 + i * 50, 50 + j * 50);
                break;
        }
        g2d.setTransform(a);
    }

    public void setMap(ArrayList<ArrayList<Double>> map) {
        this.map = map;
    }
}



