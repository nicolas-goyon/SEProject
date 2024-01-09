package com.SEApp.app.controller;

import com.SEApp.app.model.classes.Member;
import com.SEApp.app.model.classes.Role;
import com.SEApp.app.model.logic.Member.MemberFacade;
import com.SEApp.app.model.logic.exceptions.LoginException;
import com.github.fxrouter.FXRouter;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.sql.SQLException;

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
            raiseError("Please fill all the fields");
            return;
        }

        if (!passwordText.equals(confirmPasswordText)) {
            raiseError("Passwords don't match");
            return;
        }

        if (passwordText.length() < 8) {
            raiseError("Password must be at least 8 characters long");
            return;
        }

        if(usernameText.length() < 3) {
            raiseError("MemberName must be at least 3 characters long");
            return;
        }

        Member member = new Member(usernameText, emailText, passwordText, false);

        MemberFacade memberFacade = null;

        try {
            memberFacade = MemberFacade.getInstance();
        } catch (SQLException e) {
            raiseError("An error occurred, connection to the database failed", e);
            return;
        }

        boolean isRegistered = false;
        try {
            isRegistered = memberFacade.register(member);
        } catch (SQLException e) {
            raiseError("An error occurred, member creation failed", e);
            return;
        } catch (LoginException e) {
            raiseError("Email or membername already used", e);
            return;
        }

        if (isRegistered) {
            try {
                FXRouter.goTo("login", Role.MEMBER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            raiseError("Registration failed");
        }

    }

    private void raiseError(String message) {
        error.setText(message);
    }

    private void raiseError(String message, Exception e) {
        error.setText(message);
        e.printStackTrace();
    }

    public void handleLoginMemberButton() {
        try {
            FXRouter.goTo("login", Role.MEMBER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleHomeButton() {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}