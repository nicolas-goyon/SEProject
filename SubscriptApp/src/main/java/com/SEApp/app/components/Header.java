package com.SEApp.app.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


public class Header extends HBox {

    @FXML
    private Label welcomeText;


    public Header(){
        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/SEApp/app/components/Header.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();

        } catch (Exception e) {
            e.printStackTrace();
        }
        welcomeText.setText("Substrack");
    }
}
