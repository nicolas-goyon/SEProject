module com.SEApp.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;
    requires jbcrypt;

    opens com.SEApp.app to javafx.fxml;
    opens com.SEApp.app.controller to javafx.fxml;
    opens com.SEApp.app.components to javafx.fxml;

    exports com.SEApp.app;
    exports com.SEApp.app.components;
}