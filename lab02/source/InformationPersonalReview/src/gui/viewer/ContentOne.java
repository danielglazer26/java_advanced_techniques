package gui.viewer;


import service.DataFromFile;

import javax.swing.*;
import java.awt.*;

public class ContentOne extends ContentViewerFrame {

    @Override
    public void setData(DataFromFile data) {
        setText(data);
        setImage(data, 200, 100);
        pack();
    }

    @Override
    public void setText(DataFromFile data) {
        super.setText(data);
    }

    @Override
    public void setImage(DataFromFile data, int width, int height) {
        super.setImage(data, width, height);
    }

    public ContentOne() {
        super();
        changeFont(nameField, new Font(Font.DIALOG, Font.BOLD, 15));
        changeFont(nameConstLabel, new Font(Font.DIALOG, Font.BOLD, 15));
    }


    @Override
    protected void changeFont(JLabel labelName, Font fontName) {
        super.changeFont(labelName, fontName);
    }
}
