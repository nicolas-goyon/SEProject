package com.SEApp.app.controller;

import com.github.fxrouter.FXRouter;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomeController {
    public Button managersButton;
    public Button registerButton;
    public Button loginButton;

    public void initialize() {
        managersButton.setOnAction(actionEvent -> handleManagersButton());
        registerButton.setOnAction(actionEvent -> handleRegisterButton());
        loginButton.setOnAction(actionEvent -> handleLoginButton());
    }

    public void handleManagersButton() {
        try {
            FXRouter.goTo("managersManagement");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleRegisterButton() {
        try {
            FXRouter.goTo("register");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleLoginButton() {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
