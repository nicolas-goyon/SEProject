package com.SEApp.app.controller;

import com.SEApp.app.components.Header;
import com.SEApp.app.model.business.Account.UserFacade;
import com.SEApp.app.model.model.User;
import com.github.fxrouter.FXRouter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Login {


    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Text longText;

    @FXML
    private Pane myPane;

    @FXML
    protected void onLogin(){
        String chaine = "Your login is : "+ email.getText() + " and your password is " + password.getText();

        UserFacade userFacade = UserFacade.getInstance();
        User user = userFacade.login(email.getText(), password.getText());

    }


}
