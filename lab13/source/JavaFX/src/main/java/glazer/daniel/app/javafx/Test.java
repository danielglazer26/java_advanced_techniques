package glazer.daniel.app.javafx;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Test {
    public HBox xd;
    private HBox dataField;

    private TextArea xd2;

    public HBox getXd() {
        ArrayList<String> array = new ArrayList<>();
        array.add("1");
        array.add("2");
        String s = ".... dsdsdsd ....";

        for (int i = 0; i < 5; i++) {
           s = s.replaceFirst("....", array.get(i));
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showOpenDialog(xd.getScene().getWindow());
        try {
            var list = Files.readAllLines(file.toPath());
            String.join("", list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //file.toURI().toASCIIString();
        //String[] data = new String[10];
        //xd2.setText();
        //dataField.getChildren().forEach(node -> ()node);
        /*TextField textField = new TextField();
        textField.setId("1");*/
        //dataField.getChildren().clear();
        //HBox hBox = createDataField("");
        return xd;
    }

    /*public HBox createDataField(String text) {
        HBox df = new HBox();

        Label lbl = new Label(text);
        lbl.setContentDisplay(ContentDisplay.CENTER);
        lbl.setPrefHeight(25.0);
        lbl.setTextAlignment(TextAlignment.CENTER);
        lbl.setTextOverrun(OverrunStyle.WORD_ELLIPSIS);
        lbl.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
        df.getChildren().add(lbl);

        TextField tf = new TextField();
        df.getChildren().add(tf);

        HBox.setHgrow(tf, Priority.ALWAYS);
        HBox.setMargin(lbl, new Insets(10.0, 10.0, 0.0, 10.0));
        HBox.setMargin(tf, new Insets(10.0, 10.0, 10.0, 10.0));
        return df;
    }*/
}
