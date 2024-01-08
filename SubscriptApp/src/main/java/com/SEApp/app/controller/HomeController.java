package com.SEApp.app.controller;

import com.SEApp.app.model.logic.Member.MemberFacade;
import com.github.fxrouter.FXRouter;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HomeController {

    public Button loginButton;
    public Button registerButton;
    public Button logOffButton;
    public Button plansButton;
    public Button paymentTypeButton;
    public Button managersButton;
    public Button subscriptionsButton;
    public Button membersButton;

    public Button membersManagementButton;

    private List<Button> loggedButtons;
    private List<Button> notLoggedButtons;


    public void initialize() throws SQLException {
        loggedButtons = List.of(logOffButton, plansButton, paymentTypeButton, managersButton, subscriptionsButton, membersButton, membersButton);
        notLoggedButtons = List.of(loginButton, registerButton);

        MemberFacade memberFacade = MemberFacade.getInstance();
        if(memberFacade.isLogged()) {
            setLoggedButtonsVisibility(true);
            setNotLoggedButtonsVisibility(false);
        } else {
            setLoggedButtonsVisibility(false);
            setNotLoggedButtonsVisibility(true);
        }

    }

    private void setLoggedButtonsVisibility(boolean visible) {
        for(Button button : loggedButtons) {
            button.setVisible(visible);
        }
    }

    private void setNotLoggedButtonsVisibility(boolean visible) {
        for(Button button : notLoggedButtons) {
            button.setVisible(visible);
        }
    }



    public void handleManagersButton() {
        try {
            FXRouter.goTo("managersManagement");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleMembersButton() {
        try {
            FXRouter.goTo("Members list");
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
        MemberFacade memberFacade = MemberFacade.getInstance();
        memberFacade.logout();

        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handlePlansButton() {
        try {
            FXRouter.goTo("planManagement");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleSubscriptionsButton() {
        try {
            FXRouter.goTo("Member subscription");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleMembersListButton() {
        try {
            FXRouter.goTo("Members list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
