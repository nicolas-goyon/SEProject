package com.SEApp.app.controller;

import com.SEApp.app.components.ElementLogic;
import com.SEApp.app.components.GridDisplay;
import com.SEApp.app.components.ListDisplay;
import com.SEApp.app.model.classes.Access;
import com.SEApp.app.model.logic.Plan.AccessFacade;
import com.github.fxrouter.FXRouter;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class AccessManagementController {
    public ScrollPane displayPane;
    public VBox formModal;
    public VBox nameArea;
    public HBox idArea;
    public Label id;
    public TextField name;
    
    public PasswordField password;
    public VBox descriptionArea;
    public TextField description;
    public Label message;

    private AccessFacade accessFacade;

    public List<Access> accesses;

    public void initialize() {
        try {
            accessFacade = AccessFacade.getInstance();
        } catch (Exception e) {
            raiseError("Error while initializing AccessFacade", e);
            return;
        }

        try {
            accesses = accessFacade.getAllAccesses();
        } catch (Exception e) {
            raiseError("Error while getting all accesses", e);
            return;
        }

        updateDisplay();

    }

    public void updateDisplay() {
        List<ElementLogic> elements = new ArrayList<>();
        for (Access access : accesses) {
            elements.add(new ElementLogic(access.getId(), access.getName(), access.getDescription()));
        }

        GridDisplay gridDisplay = new GridDisplay(elements, this::callbackEdit, this::callbackDelete);

        displayPane.setContent(gridDisplay);
    }

    public Void callbackEdit(Integer id){
        Access access = getAccessFromListById(id);

        if (access == null) {
            raiseError("Error while getting access id can be incorrect : " + id);
            return null;
        }

        this.id.setText(String.valueOf(access.getId()));
        name.setText(access.getName());
        description.setText(access.getDescription());
        formModal.setVisible(true);
        return null;
    }

    public Void callbackDelete(Integer id){
        Access access = getAccessFromListById(id);

        if (access == null) {
            raiseError("Error while getting access id can be incorrect : " + id);
            return null;
        }

        boolean isDeleted = false;
        try {
            isDeleted = accessFacade.deleteAccess(access);
        } catch (Exception e) {
            raiseError("Error while deleting access", e);
            return null;
        }

        if(!isDeleted){
            raiseError("Error while deleting access");
            return null;
        }

        accesses.remove(access);
        updateDisplay();
        return null;
    }


    public void handleHomeButton() {
        try {
            FXRouter.goTo("home");
        } catch (Exception e) {
            raiseError("Error while going to home", e);
        }
    }

    public void handleNewButton() {
        id.setText("New");
        name.setText("");
        description.setText("");
        formModal.setVisible(true);
    }

    public void handleMemberCreation() {
        if(!id.getText().equals("New")){
            raiseError("Cannot create new access while editing an existing one");
            return;
        }

        if(name.getText().isEmpty()){
            raiseError("Name cannot be empty");
            return;
        }

        if(description.getText().isEmpty()){
            raiseError("Description cannot be empty");
            return;
        }

        Access access = null;
        try {
            access = accessFacade.createAccess(name.getText(), description.getText());


        } catch (Exception e) {
            raiseError("Error while creating new access", e);
            return;
        }
        if (access == null) {
            raiseError("Error while creating new access");
            return;
        }

        accesses.add(access);
        updateDisplay();
        formModal.setVisible(false);
    }

    public void handleMemberUpdate() {
        if(id.getText().equals("new")){
            raiseError("Cannot update new access while creating a new one");
            return;
        }

        if(name.getText().isEmpty()){
            raiseError("Name cannot be empty");
            return;
        }

        if(description.getText().isEmpty()){
            raiseError("Description cannot be empty");
            return;
        }

        Access access = getAccessFromListById(Integer.parseInt(id.getText()));

        if (access == null) {
            raiseError("Error while getting access");
            return;
        }

        access.setName(name.getText());
        access.setDescription(description.getText());

        try {
            accessFacade.updateAccess(access);
        } catch (Exception e) {
            raiseError("Error while updating access", e);
            return;
        }

        updateDisplay();
        formModal.setVisible(false);
    }

    private Access getAccessFromListById(int id){
        return accesses.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }

    public void closeModal() {
        formModal.setVisible(false);
    }

    private void raiseError(String message) {
        this.message.setText(message);
        System.err.println(message);
    }

    private void raiseError(String message, Exception e) {
        raiseError(message);
        e.printStackTrace();
    }
}
