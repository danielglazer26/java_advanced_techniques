package service.data;

import javax.swing.*;
import java.io.Serializable;

public class DataFromFile implements Serializable {
    private String name;
    private String surname;
    private String age;
    private String placeOfResidence;
    private ImageIcon image;

    public DataFromFile(String[] data, ImageIcon image) {
        this.name = data[0];
        this.surname = data[1];
        this.age = data[2];
        this.placeOfResidence = data[3];
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
