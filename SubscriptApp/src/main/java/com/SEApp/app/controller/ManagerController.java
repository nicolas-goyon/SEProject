package com.SEApp.app.controller;

import com.SEApp.app.components.ElementLogic;
import com.SEApp.app.components.GridDisplay;
import com.SEApp.app.model.classes.Manager;
import com.SEApp.app.model.logic.Manager.ManagerFacade;
import com.github.fxrouter.FXRouter;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * 
 */
public class ManagerController {

    public VBox confirmPasswordArea;
    public TextField confirmPassword;
    public TextField password;
    public VBox passwordArea;
    public HBox idArea;
    public TextField email;
    public VBox emailArea;
    public VBox nameArea;
    public TextField name;
    public Label id;
    public Label message;
    public ScrollPane displayPane;

    private List<Manager> managerList;
    public VBox formModal;

    private static ManagerFacade facade;

    public void initialize() {

        try {
            facade = ManagerFacade.getInstance();
        } catch (SQLException e) {
            message.setText("Error while loading managers : connection error");
            System.err.println("Error while loading managers : connection error");
            return;
        }
        if (facade == null) {
            message.setText("Error while loading managers : instance error");
            System.err.println("Error while loading managers : instance error");
            return;
        }
        try {
            managerList = facade.getAllManagers();
        } catch (Exception e) {
            message.setText("Error while loading managers : get error");
            System.err.println("Error while loading managers : get error");
            return;
        }

        listUpdate();

    }


    public void listUpdate(){
        List<ElementLogic> list = new ArrayList<>();

        for (Manager manager : managerList) {
            list.add(new ElementLogic(manager.getId(), manager.getUsername(), manager.getEmail()));
        }

        GridDisplay gridDisplay = new GridDisplay(list, this::editButtonPressed, this::deleteButtonPressed);
        displayPane.setContent(gridDisplay);
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
        int index = managerList.indexOf(managerList.stream().filter(manager -> manager.getId() == id).toList().get(0));
        Manager manager = managerList.get(index);
        this.id.setText(String.valueOf(manager.getId()));
        name.setText(manager.getUsername());
        email.setText(manager.getEmail());
//        passwordArea.setVisible(false);
//        confirmPasswordArea.setVisible(false);


        return null;
    }

    public Void deleteButtonPressed(Integer id){
        boolean isDeleted = false;
        try {
            isDeleted = facade.deleteManager(managerList.stream().filter(manager -> manager.getId() == id).toList().get(0));
        } catch (Exception e) {
            message.setText("Error while deleting manager : delete error");
            System.err.println("Error while deleting manager : delete error");
            return null;
        }

        if (isDeleted) {
            message.setText("Manager deleted successfully");
        } else {
            message.setText("Error while deleting manager : manager not deleted");
            System.err.println("Error while deleting manager : manager not deleted");
            return null;
        }

        managerList.removeIf(element -> element.getId() == id);
        listUpdate();
        return null;
    }






    /**
     * 
     */
    public void handleManagerCreation() {
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

        Manager manager = new Manager(nameS, emailS, passwordS, false);

        boolean isCreated = false;
        try {
            isCreated = facade.createManager(manager);
        } catch (Exception e) {
            message.setText("Error while creating manager : connection error");
            return;
        }

        if (isCreated) {
            message.setText("Manager created successfully !");
        } else {
            message.setText("Error while creating manager : manager not created");
        }

        managerList.add(manager);
        listUpdate();

    }

    /**
     * 
     */
    public void handleManagerUpdate() {
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

        // filter the list to get the manager with the id
        List<Manager> filteredList = managerList.stream().filter(manager -> manager.getId() == Integer.parseInt(id.getText())).toList();
        Manager manager = filteredList.get(0);
        Manager newManager = new Manager(manager.getId(), manager.getUsername(), manager.getEmail(), manager.getPassword(), true);

        if (!nameS.isEmpty()) {
            newManager.setUsername(nameS);
        }

        if (!emailS.isEmpty()) {
            newManager.setEmail(emailS);
        }

        if (!passwordS.isEmpty()) {
            newManager.setPassword(passwordS, false);
        }

        boolean isUpdated = false;

        try {
            isUpdated = facade.updateManager(newManager);
        } catch (Exception e) {
            message.setText("Error while updating manager : connection error");
            return;
        }

        if (isUpdated) {
            message.setText("Manager updated successfully");
        } else {
            message.setText("Error while updating manager : manager not updated");
            return;
        }

        managerList.set(managerList.indexOf(manager), newManager);
        listUpdate();

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


    public void handleHomeButton() {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}