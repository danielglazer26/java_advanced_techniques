Znając zasady korzystania ze skryptów JS w programach napisanych w języku Java można pójść o krok dalej w zabawie z tymi
technologiami. Zdobytą wiedzę można wykorzystać podczas budowy aplikacji z graficznym interfejsem użytkownika
opierającym się na klasach JavaFX.

Zadanie polega na zaimplementowaniu aplikacji korzystającej z JavaFX w niestandardowy sposób. Ma to polegać na
oskryptowaniu całej logiki w pliku fxml zamiast w kontrolerze napisanym w języku Java. W języku Java ma być
zaimplementowane tylko ładowanie fxmla. Proszę spojrzeć na przykłady zamieszczone poniżej (Sample1.fxml, Sample1.java,
application.css).

Sama aplikacja ma pozwalać na generowanie zaproszeń na różnego rodzaju towarzyskie imprezy (parapetówki, andrzejki,
sylwestra, rocznice ...) na podstawie spreparowanych wcześniej szablonów uzupełnianych niezbędnymi atrybutami (datą,
miejscem, imieniem, ...). Wygenerowane zaproszenia mogą pojawiać się na interfejsie użytkownika jako tekst, który
powinno dać się skopiować. Szablony powinny być zapisane w plikach konfiguracyjnych.

Do przemyślenia jest, w jaki sposób użytkownik ma przekazywać do aplikacji niezbędne atrybuty (liczba i typ atrybutów
może zależeć od rodzaju szablonu).

W gitowym repozytorium w gałęzi sources należy umieścić wszystkie źródła plus plik Readme.md (z dołączonymi zrzutami i
opisem, jak uruchomić aplikację), w gałęzi releases - wykonywalny plik jar (z przygotowaniem jara może być problem -
trzeba sprawdzić, czy pliki fxml, css oraz szablonów będą się ładować z tego jara, ostatecznie można je dystrybuować
osobno).

### Sample1.fxml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?language javascript?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>

<VBox fx:id="vbox" layoutX="10.0" layoutY="10.0" prefHeight="250.0"
      prefWidth="300.0" spacing="10" xmlns:fx="http://javafx.com/fxml/1"
      xmlns="http://javafx.com/javafx/2.2">

    <fx:script>
        var System = Java.type("java.lang.System")
        function buttonAction(event){
            java.lang.System.out.println("finally we get to print something.");
            outputLbl.setText("Your Input please:");
        }
    </fx:script>
    <children>

        <Label fx:id="inputLbl" alignment="CENTER_LEFT" cache="true"
               cacheHint="SCALE" prefHeight="30.0" prefWidth="200.0"
               text="Please insert Your Input here:" textAlignment="LEFT"/>
        <TextField fx:id="inputText" prefWidth="100.0"/>
        <Button fx:id="okBtn" alignment="CENTER_RIGHT"
                contentDisplay="CENTER" mnemonicParsing="false"
                onAction="buttonAction(event);" text="OK" textAlignment="CENTER"/>

        <Label fx:id="outputLbl" alignment="CENTER_LEFT" cache="true"
               cacheHint="SCALE" prefHeight="30.0" prefWidth="200.0"
               text="Your Input:" textAlignment="LEFT"/>
        <TextArea fx:id="outputText" prefHeight="100.0"
                  prefWidth="200.0" wrapText="true"/>
    </children>
</VBox>
```

### Sample1.java

```java
package application;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Sample1 extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        String fxmlDocPath = "src/Sample1.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

        VBox root = (VBox) loader.load(fxmlStream);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("A FXML Example without any Controller");
        stage.show();

    }
}
```

### application.css  ###########

```css

/* JavaFX CSS - Leave this comment until you have at least create one rule which uses -fx-Property */
.pane {
    -fx-background-color: #1d1d1d;
}

.root {
    -fx-padding: 10;
    -fx-border-style: solid inside;
    -fx-border-width: 2;
    -fx-border-insets: 5;
    -fx-border-radius: 5;
    -fx-border-color: #2e8b57;
    -fx-background-color: #1d1d1d;
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 0.6;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #1d1d1d;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
    -fx-background-color: white;
    -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}
```