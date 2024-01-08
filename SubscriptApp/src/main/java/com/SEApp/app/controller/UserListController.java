package com.SEApp.app.controller;

import com.SEApp.app.components.ElementLogic;
import com.SEApp.app.components.ListDisplay;
import com.SEApp.app.model.classes.User;
import com.SEApp.app.model.logic.Subscription.SubscriptionFacade;
import com.SEApp.app.model.logic.account.UserFacade;
import com.github.fxrouter.FXRouter;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserListController {
    public ScrollPane displayPane;

    private List<User> users;

    public VBox formModal;

    public Label id;

    private List<User> usersList;

    public VBox confirmPasswordArea;
    public TextField confirmPassword;
    public TextField password;
    public VBox passwordArea;
    public HBox idArea;
    public TextField email;
    public VBox emailArea;
    public VBox nameArea;
    public TextField name;

    public Label message;

    private static UserFacade facade;
    



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

    public void handleHomeButton() {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleNewButton(){
        formModal.setVisible(true);
        id.setText("New");
    }
    public void closeModal(){
        formModal.setVisible(false);
    }

    public Void editButtonPressed(Integer id){
        formModal.setVisible(true);
        int index = usersList.indexOf(usersList.stream().filter(user -> user.getId() == id).toList().get(0));
        User user = usersList.get(index);
        this.id.setText(String.valueOf(user.getId()));
        name.setText(user.getUsername());
        email.setText(user.getEmail());
//        passwordArea.setVisible(false);
//        confirmPasswordArea.setVisible(false);


        return null;
    }

    public Void deleteButtonPressed(Integer id){

        try {
            facade.deleteUser(usersList.stream().filter(user -> user.getId() == id).toList().get(0));
        } catch (Exception e) {
            message.setText("Error while deleting user : delete error");
            System.err.println("Error while deleting user : delete error");
            return null;
        }

        usersList.removeIf(element -> element.getId() == id);
        updateList();
        return null;
    }

    public void handleUserCreation() {
        String nameS = name.getText();
        String emailS = email.getText();
        String passwordS = password.getText();
        String confirmPasswordS = confirmPassword.getText();

        if (nameS.isEmpty() || emailS.isEmpty() || passwordS.isEmpty() || confirmPasswordS.isEmpty()) {
            message.setText("Please fill all the fields");
            return;
        }

        if (!passwordValidation(passwordS, confirmPasswordS) || !emailValidation(emailS)) {
            return;
        }

        User user = new User(nameS, emailS, passwordS, false);

        boolean isCreated = false;
        try {
            isCreated = facade.createUser(user);
        } catch (Exception e) {
            message.setText("Error while creating user : connection error");
            return;
        }

        if (isCreated) {
            message.setText("user created successfully !");
        } else {
            message.setText("Error while creating user : user not created");
        }

        usersList.add(user);
        updateList();

    }

    /**
     *
     */
    public void handleUserUpdate() {
        String nameS = name.getText();
        String emailS = email.getText();
        String passwordS = password.getText();
        String confirmPasswordS = confirmPassword.getText();

        if (nameS.isEmpty() && emailS.isEmpty() && passwordS.isEmpty() && confirmPasswordS.isEmpty()) {
            message.setText("Please fill at least one field");
            return;
        }

        if (!passwordS.equals(confirmPasswordS) || !emailValidation(emailS)) {
            return;
        }

        // filter the list to get the user with the id
        List<User> filteredList = usersList.stream().filter(user -> user.getId() == Integer.parseInt(id.getText())).toList();
        User user = filteredList.get(0);
        User newuser = new User(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), true);

        if (!nameS.isEmpty()) {
            newuser.setUsername(nameS);
        }

        if (!emailS.isEmpty()) {
            newuser.setEmail(emailS);
        }

        if (!passwordS.isEmpty()) {
            newuser.setPassword(passwordS, false);
        }

        boolean isUpdated = false;

        try {
            isUpdated = facade.updateUser(newuser);
        } catch (Exception e) {
            message.setText("Error while updating user : connection error");
            return;
        }

        if (isUpdated) {
            message.setText("user updated successfully");
        } else {
            message.setText("Error while updating user : user not updated");
            return;
        }

        usersList.set(usersList.indexOf(user), newuser);
        updateList();

    }

    public boolean emailValidation(String email) {
        if (!email.contains("@")) {
            message.setText("Email is not valid");
            return false;
        }

        if (!email.contains(".")) {
            message.setText("Email is not valid");
            return false;
        }

        if (email.contains(" ")) {
            message.setText("Email is not valid");
            return false;
        }

        if (email.contains("@.")) {
            message.setText("Email is not valid");
            return false;
        }

        if (email.contains(".@")) {
            message.setText("Email is not valid");
            return false;
        }
        return true;
    }

    public boolean passwordValidation(String password , String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            message.setText("Passwords don't match");
            return false;
        }

        if (password.length() < 8) {
            message.setText("Password must be at least 8 characters long");
            return false;
        }
        return true;
    }
}
