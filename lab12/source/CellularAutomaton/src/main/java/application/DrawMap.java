package application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class DrawMap extends JPanel {

    private int map_size;
    private int window_resizable_x;

    private int window_resizable_y;

    private ArrayList<ArrayList<Double>> map;

    /**
     * Wywoluje metody od rysowania mapy i legendy
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(((float) (window_resizable_x)) / 50, ((float) (window_resizable_y)) / 50);
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

                    if (mapValue != 0)
                        makeRotation(mapValue, g2d, i, j);

                    g2d.setPaint(Color.BLACK);
                }

            }


    }

    private BufferedImage getImage(Color color, URL path) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(path);
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

    private void makeRotation(double direction, Graphics2D g2d, int i, int j) {
        Color red = new Color(255, 0, 0);
        switch ((int) direction) {
            case 1:
                g2d.drawImage(getImage(red, this.getClass().getResource("ant.png")),
                        10 + i * 50, 10 + j * 50, null);
                break;
            case 2:
                g2d.drawImage(getImage(red, this.getClass().getResource("antD.png")),
                        10 + i * 50, 10 + j * 50, null);
                break;
            case 3:
                g2d.drawImage(getImage(red, this.getClass().getResource("antP.png")),
                        10 + i * 50, 10 + j * 50, null);
                break;
            case 4:
                g2d.drawImage(getImage(red, this.getClass().getResource("antG.png")),
                        10 + i * 50, 10 + j * 50, null);
                break;
        }
    }

    public void setMap(ArrayList<ArrayList<Double>> map) {
        this.map = map;
    }

    public void setMap_size(int map_size) {
        this.map_size = map_size;
    }

    public void setWindow_resizable_x(int window_resizable_x) {
        this.window_resizable_x = window_resizable_x;
    }

    public void setWindow_resizable_y(int window_resizable_y) {
        this.window_resizable_y = window_resizable_y;
    }
}



