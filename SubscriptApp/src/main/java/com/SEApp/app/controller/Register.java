package com.SEApp.app.controller;

import com.SEApp.app.model.classes.User;
import com.SEApp.app.model.logic.account.UserFacade;
import com.github.fxrouter.FXRouter;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * 
 */
public class Register {

    public TextField email;
    public PasswordField password;
    public PasswordField confirmPassword;
    public Label error;
    public TextField username;


    /**
     * Default constructor
     */
    public Register() {
    }

    /**
     * 
     */
    public void onRegister() {
        String emailText = email.getText();
        String passwordText = password.getText();
        String confirmPasswordText = confirmPassword.getText();
        String usernameText = username.getText();

        if (emailText.isEmpty() || passwordText.isEmpty() || confirmPasswordText.isEmpty() || usernameText.isEmpty()) {
            error.setText("Please fill all the fields");
            return;
        }

        if (!passwordText.equals(confirmPasswordText)) {
            error.setText("Passwords don't match");
            return;
        }

        if (passwordText.length() < 8) {
            error.setText("Password must be at least 8 characters long");
            return;
        }

        if(usernameText.length() < 3) {
            error.setText("Username must be at least 3 characters long");
            return;
        }

        User user = new User(usernameText, emailText, passwordText, "user", false);

        UserFacade userFacade = null;

        try {
            userFacade = UserFacade.getInstance();
        } catch (SQLException e) {
            error.setText("An error occurred, connection to the database failed");
            return;
        }

        boolean isRegistered = false;
        try {
            isRegistered = userFacade.register(user);
        } catch (SQLException e) {
            error.setText("An error occurred, user creation failed");
            return;
        }

        if (isRegistered) {
            try {
                FXRouter.goTo("login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            error.setText("Email or username already used");
        }

    }

}