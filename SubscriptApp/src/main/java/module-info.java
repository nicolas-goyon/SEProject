module org.openjfx.javafxmavenarchetypes {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.openjfx.javafxmavenarchetypes to javafx.fxml;
    exports org.openjfx.javafxmavenarchetypes;
}