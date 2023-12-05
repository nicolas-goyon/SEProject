package com.SEApp.app;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private Label name;

    public void setName(String name){
        this.name.setText(name);
    }
}
