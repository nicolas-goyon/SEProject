package com.SEApp.app.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.function.Function;

public class ListElement extends HBox {

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Button subscriptionButton;

    @FXML
    private Function<Integer, Void> callBackSubscription;

    private final int id;

    public ListElement(int id, Function<Integer, Void> callBackSubscription) {
        this.id = id;
        this.callBackSubscription = callBackSubscription;

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
        setSubscriptionButtonAction(callBackSubscription);
    }


    public void setSubscriptionButtonText(String text) {
        subscriptionButton.setText(text);
    }

    public void setSubscriptionButtonAction(Function<Integer, Void> callback) {
        if(callback == null) {
            subscriptionButton.setVisible(false);
            return;
        }
        subscriptionButton.setOnAction(event -> callback.apply(id));
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setDescription(String description) {
        descriptionLabel.setText(description);
    }
}
