package com.SEApp.app.controller;

import com.SEApp.app.components.ElementLogic;
import com.SEApp.app.components.ListDisplay;
import com.SEApp.app.model.classes.User;
import com.SEApp.app.model.logic.Subscription.SubscriptionFacade;
import com.SEApp.app.model.logic.account.UserFacade;
import com.github.fxrouter.FXRouter;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserListController {
    public ScrollPane displayPane;

    private List<User> users;



    public void initialize() {
        UserFacade userFacade = getUserFacade();
        if (userFacade == null) {
            return;
        }

        List<User> users = null;

        try {
            users = userFacade.getAllUsers();
        } catch (Exception e) {
            raiseError("Could not get users", e);
        }

        if (users == null) {
            raiseError("Could not get users");
            return;
        }

        this.users = users;

        updateList();
    }

    /**
     *
     */
    public void updateList() {
        List<ElementLogic> list = new ArrayList<>();
        for (User user : users) {
            list.add(new ElementLogic(user.getId(), user.getUsername(), user.getEmail()));
        }

        ListDisplay listDisplay = new ListDisplay(list, this::callbackSubscription);
        displayPane.setContent(listDisplay);
    }

    public Void callbackSubscription(Integer id) {
        UserFacade userFacade = getUserFacade();
        if (userFacade == null) {
            return null;
        }

        User user = null;
        try {
            user = userFacade.getUserById(id);
        } catch (Exception e) {
            raiseError("Could not get current user", e);
        }

        if (user == null) {
            raiseError("You must be logged in to subscribe to a plan");
            return null;
        }
        System.out.printf(user.getUsername());
        SubscriptionFacade.setManagerialUser(user);

        try {
            FXRouter.goTo("ManagerialSubscription");
        } catch (IOException e) {
            raiseError("Could not go to subscription page", e);
        }

        return null;
    }

    private UserFacade getUserFacade() {
        UserFacade userFacade = null;
        try{
            userFacade = UserFacade.getInstance();
        }catch (Exception e){
            raiseError("Could not connect to database", e);
        }

        if (userFacade == null) {
            raiseError("Could not connect to database");
            return null;
        }

        return userFacade;
    }

    private SubscriptionFacade getSubscriptionFacade() {
        SubscriptionFacade subscriptionFacade = null;
        try{
            subscriptionFacade = SubscriptionFacade.getInstance();
        }catch (Exception e){
            raiseError("Could not connect to database", e);
        }

        if (subscriptionFacade == null) {
            raiseError("Could not connect to database");
            return null;
        }

        return subscriptionFacade;
    }

    private void raiseError(String message, Exception e) {
        raiseError(message);
        e.printStackTrace();
    }

    private void raiseError(String message) {
        displayPane.setContent(new Label("Error: " + message));
        System.err.println(message);
    }
}
