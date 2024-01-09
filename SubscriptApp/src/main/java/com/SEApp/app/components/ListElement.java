package com.SEApp.app.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.Function;

public class ListElement extends HBox {

    private final List<ButtonData> buttonsData;
    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Button subscriptionButton;

    @FXML
    private HBox buttonArea;


    private final int id;

    public ListElement(int id, List<ButtonData> buttonData) {
        this.id = id;
        this.buttonsData = buttonData;

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/SEApp/app/components/ListElement.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initialize() {
        buttonArea.getChildren().clear();

        // Add the buttons
        for (ButtonData buttonData : buttonsData) {
            Button button = new Button(buttonData.getName());
            button.setOnAction(event -> buttonData.getCallback().apply(id));
            buttonArea.getChildren().add(button);
        }
    }


    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setDescription(String description) {
        descriptionLabel.setText(description);
    }
}
