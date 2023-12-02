module org.openjfx.javafxmavenarchetypes {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.openjfx.javafxmavenarchetypes to javafx.fxml;
    exports org.openjfx.javafxmavenarchetypes;
}