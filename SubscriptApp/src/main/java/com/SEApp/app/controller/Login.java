package com.SEApp.app.controller;

import com.SEApp.app.model.classes.Role;
import com.SEApp.app.model.logic.Manager.ManagerFacade;
import com.SEApp.app.model.logic.Member.MemberFacade;
import com.SEApp.app.model.logic.account.AdminFacade;
import com.SEApp.app.model.logic.account.UserFacade;
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
    public Label title;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label error;


    // --- Attributes ---
    private Role role;

    private UserFacade userFacade;

    public void initialize() {
        this.role = (Role) FXRouter.getData();

        switch (role) {
            case ADMIN:
                title.setText("Admin Login");
                break;
            case MANAGER:
                title.setText("Manager Login");
                break;
            case MEMBER:
                title.setText("Member Login");
                break;
        }

        userFacade = null;
        try {
            switch (role) {
                case ADMIN:
                    userFacade = AdminFacade.getInstance();
                    break;
                case MANAGER:
                    userFacade = ManagerFacade.getInstance();
                    break;
                case MEMBER:
                    userFacade = MemberFacade.getInstance();
                    break;
            }
        } catch (SQLException e) {
            raiseError("An error occurred, connection to the database failed", e);
        }
    }


    @FXML
    protected void onLogin() {
        boolean isLog = false;

        try {
            isLog = userFacade.login(email.getText(), password.getText());
        } catch (SQLException | RuntimeException e) {
            raiseError("An error occurred, connection to the database failed", e);
            return;
        } catch (LoginException e) {
            raiseError("Wrong email or password", e);
            return;
        }

        if (isLog) {
            try {
                FXRouter.goTo("home");
            } catch (IOException e) {
                raiseError("Page changing error", e);
                return;
            }
        }
        else {
            raiseError("Wrong email or password");
            return;
        }

    }

    private void raiseError(String message) {
        error.setText(message);
    }

    private void raiseError(String message, Exception e) {
        raiseError(message);
        e.printStackTrace();
    }
}
