package gui.viewer;

import service.DataFromFile;

import javax.swing.*;
import java.awt.*;

public class ContentViewerFrame extends JFrame {
    protected JPanel panel1;
    protected JLabel nameConstLabel;
    protected JLabel nameField;
    protected JLabel surnameField;
    protected JLabel surnameConstLabel;
    protected JLabel ageField;
    protected JLabel ageConstField;
    protected JLabel placeOfResidenceField;
    protected JLabel placeOfResidenceConstLabel;
    protected JLabel imageLabel;
    protected JPanel imagePanel;

    public ContentViewerFrame() {
        setContentPane(panel1);
    }

    public void setData(DataFromFile data) {
        setText(data);
        setImage(data, 100, 50);
        pack();
    }


    protected void setText(DataFromFile data) {
        nameField.setText(data.getName());
        surnameField.setText(data.getSurname());
        ageField.setText(data.getAge());
        placeOfResidenceField.setText(data.getPlaceOfResidence());

    }

    protected void setImage(DataFromFile data, int width, int height) {
        Image img = data.getImage().getImage();
        imageLabel.setIcon(new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
    }

    protected void changeFont(JLabel labelName, Font fontName) {
        labelName.setFont(fontName);
    }

}
