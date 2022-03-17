package service;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class DataFromFile {
    private String name;
    private String surname;
    private String age;
    private String gender;
    private String placeOfResidence;
    private ImageIcon image;

    public DataFromFile(String[] data, ImageIcon image) {
        this.name = data[0];
        this.surname = data[1];
        this.age = data[2];
        this.gender = data[3];
        this.placeOfResidence = data[4];
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPlaceOfResidence() {
        return placeOfResidence;
    }

    public void setPlaceOfResidence(String placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }
}
