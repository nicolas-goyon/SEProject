package com.SEApp.app.controller;

import com.SEApp.app.model.classes.Logged;
import com.SEApp.app.model.classes.Role;
import com.SEApp.app.model.logic.Member.MemberFacade;
import com.SEApp.app.model.logic.account.UserFacade;
import com.github.fxrouter.FXRouter;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;

public class HomeController {


    // ---- BUTTONS ----
    public Button registerButton;
    public Button logOffButton;
    public Button plansButton;
    public Button paymentTypeButton;
    public Button managersButton;
    public Button subscriptionsButton;
    public Button membersButton;



    // ---- ZONES ----
    public VBox managerLoggedZone;
    public VBox adminLoggedZone;
    public VBox memberLoggedZone;
    public VBox allUserLoggedZone;
    public VBox notLoggedZone;




    public void initialize() throws SQLException {
        Logged logged = Logged.getInstance();

        // default display
        notLoggedZone.setVisible(true);
        allUserLoggedZone.setVisible(false);
        memberLoggedZone.setVisible(false);
        adminLoggedZone.setVisible(false);
        managerLoggedZone.setVisible(false);

        if (!logged.isLogged()) {
            return;
        }

        allUserLoggedZone.setVisible(true);
        notLoggedZone.setVisible(false);

        switch (logged.getUser().getRole()) {
            case ADMIN:
                adminLoggedZone.setVisible(true);
                break;
            case MANAGER:
                managerLoggedZone.setVisible(true);
                break;
            case MEMBER:
                memberLoggedZone.setVisible(true);
                break;
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



    public void handlePaymentTypeButton() {
        try {
            FXRouter.goTo("paymentTypesManagement");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleLogOffButton() throws SQLException {
        UserFacade userFacade = MemberFacade.getInstance();
        userFacade.logout();

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

    public void handleLoginMemberButton() {
        try {
            FXRouter.goTo("login", Role.MEMBER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleLoginManagerButton() {
        try {
            FXRouter.goTo("login", Role.MANAGER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleLoginAdminButton() {
        try {
            FXRouter.goTo("login", Role.ADMIN);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleAccessButton() {
        try {
            FXRouter.goTo("accessManagement");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handlePlanAccessButton() {
        try {
            FXRouter.goTo("planAccessManagement");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
