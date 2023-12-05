module com.SEApp.app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.SEApp.app to javafx.fxml;
    exports com.SEApp.app;
}