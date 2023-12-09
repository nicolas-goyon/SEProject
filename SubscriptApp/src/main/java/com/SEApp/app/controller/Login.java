package com.SEApp.app.controller;

import com.SEApp.app.model.logic.account.UserFacade;
import com.github.fxrouter.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class Login {


    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label error;


    @FXML
    protected void onLogin() {
        UserFacade userFacade = null;
        try {
            userFacade = UserFacade.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        boolean isLog = false;
        try {
            isLog = userFacade.login(email.getText(), password.getText());
        } catch (SQLException | RuntimeException e) {
            error.setText("An error occurred, connection to the database failed");
            e.printStackTrace();
        }

        if (isLog) {
            error.setText("Login successful");
            /*try {
                FXRouter.goTo("logged");
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        else {
            error.setText("Wrong email or password");
        }

    }
}
