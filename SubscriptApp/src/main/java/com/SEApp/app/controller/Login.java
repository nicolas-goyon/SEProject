package com.SEApp.app.controller;

import com.SEApp.app.model.logic.Member.MemberFacade;
import com.SEApp.app.model.logic.exceptions.LoginException;
import com.github.fxrouter.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

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
        MemberFacade userFacade = null;
        try {
            userFacade = MemberFacade.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        boolean isLog = false;
        try {
            isLog = userFacade.login(email.getText(), password.getText());
        } catch (SQLException | RuntimeException e) {
            error.setText("An error occurred, connection to the database failed");
            e.printStackTrace();
        } catch (LoginException e) {
            error.setText("Wrong email or password");
            e.printStackTrace();
        }

        if (isLog) {
            try {
                FXRouter.goTo("home");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            error.setText("Wrong email or password");
        }

    }
}
