package com.SEApp.app.controller;

import com.SEApp.app.model.logic.account.UserFacade;
import com.github.fxrouter.FXRouter;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;

public class HomeController {

    public Button loginButton;
    public Button registerButton;
    public Button logOffButton;


    public void initialize() throws SQLException {
        UserFacade userFacade = UserFacade.getInstance();
        if(userFacade.isLogged()) {
            loginButton.setVisible(false);
            registerButton.setVisible(false);
            logOffButton.setVisible(true);
        } else {
            loginButton.setVisible(true);
            registerButton.setVisible(true);
            logOffButton.setVisible(false);
        }
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


    public void handlePaymentTypeButton() {
        try {
            FXRouter.goTo("paymentTypesManagement");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleLogOffButton() throws SQLException {
        UserFacade userFacade = UserFacade.getInstance();
        userFacade.logout();

        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
