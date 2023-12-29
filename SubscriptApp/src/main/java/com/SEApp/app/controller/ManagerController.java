package com.SEApp.app.controller;

import com.SEApp.app.components.ElementLogic;
import com.SEApp.app.components.ListDisplay;
import com.SEApp.app.model.classes.Manager;
import com.SEApp.app.model.logic.Manager.ManagerFacade;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.*;
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

    private ArrayList<Manager> managerList;
    public VBox formModal;

    public void initialize() {

        managerList = new ArrayList<>();
        managerList.add(new Manager(1, "testName1", "testEmail1", "testPassword1", false));
        managerList.add(new Manager(2, "testName2", "testEmail2", "testPassword2", false));
        managerList.add(new Manager(3, "testName3", "testEmail3", "testPassword3", false));
        managerList.add(new Manager(4, "testName4", "testEmail4", "testPassword4", false));
        managerList.add(new Manager(5, "testName5", "testEmail5", "testPassword5", false));
        managerList.add(new Manager(6, "testName6", "testEmail6", "testPassword6", false));
        managerList.add(new Manager(7, "testName7", "testEmail7", "testPassword7", false));
        managerList.add(new Manager(8, "testName8", "testEmail8", "testPassword8", false));
        managerList.add(new Manager(9, "testName9", "testEmail9", "testPassword9", false));
        managerList.add(new Manager(10, "testName10", "testEmail10", "testPassword10", false));

        
        listUpdate();

    }


    public void listUpdate(){
        List<ElementLogic> list = new ArrayList<>();

        for (Manager manager : managerList) {
            list.add(new ElementLogic(manager.getId(), manager.getUsername(), manager.getEmail()));
        }

        ListDisplay listDisplay = new ListDisplay(list, this::editButtonPressed, this::deleteButtonPressed);
        displayPane.setContent(listDisplay);
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
        this.id.setText(id.toString());

        return null;
    }

    public Void deleteButtonPressed(Integer id){
        managerList.removeIf(element -> element.getId() == id);

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
        ManagerFacade facade = ManagerFacade.getInstance();

        if (facade == null) {
            message.setText("Error while creating manager");
            return;
        }

        if (facade.createManager(manager)) {
            message.setText("Manager created successfully");
        } else {
            message.setText("Error while creating manager");
        }

    }

    /**
     * 
     */
    public void handleManagerUpdate() {
        // TODO implement here
    }

    /**
     * 
     */
    public void handleManagerDeletion() {
        // TODO implement here
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