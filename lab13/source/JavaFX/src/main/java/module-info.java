module glazer.daniel.app.javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens glazer.daniel.app.javafx to javafx.fxml;
    exports glazer.daniel.app.javafx;
}