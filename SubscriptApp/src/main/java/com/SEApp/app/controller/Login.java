package com.SEApp.app.controller;

import com.SEApp.app.model.business.Account.UserFacade;
import com.github.fxrouter.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Login {


    @FXML
    private TextField email;

    @FXML
    private PasswordField password;


    @FXML
    protected void onLogin() {
        UserFacade userFacade = UserFacade.getInstance();
        boolean user = userFacade.login(email.getText(), password.getText());

        if (user) {
            try {
                FXRouter.goTo("logged");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
