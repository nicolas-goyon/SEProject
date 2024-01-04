package com.SEApp.app.controller;

import com.SEApp.app.components.ElementLogic;
import com.SEApp.app.components.GridDisplay;
import com.SEApp.app.model.classes.Member;
import com.SEApp.app.model.logic.Member.MemberFacade;
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
public class MemberController {

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

    private List<Member> MemberList;
    public VBox formModal;

    private static MemberFacade facade;

    public void initialize() {

        try {
            facade = MemberFacade.getInstance();
        } catch (SQLException e) {
            message.setText("Error while loading Members : connection error");
            System.err.println("Error while loading Members : connection error");
            return;
        }
        if (facade == null) {
            message.setText("Error while loading Members : instance error");
            System.err.println("Error while loading Members : instance error");
            return;
        }
        try {
            MemberList = facade.getAllMembers();
        } catch (Exception e) {
            message.setText("Error while loading Members : get error");
            System.err.println("Error while loading Members : get error");
            return;
        }

        listUpdate();

    }


    public void listUpdate(){
        List<ElementLogic> list = new ArrayList<>();

        for (Member Member : MemberList) {
            list.add(new ElementLogic(Member.getId(), Member.getUsername(), Member.getEmail()));
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
        int index = MemberList.indexOf(MemberList.stream().filter(Member -> Member.getId() == id).toList().get(0));
        Member Member = MemberList.get(index);
        this.id.setText(String.valueOf(Member.getId()));
        name.setText(Member.getUsername());
        email.setText(Member.getEmail());
//        passwordArea.setVisible(false);
//        confirmPasswordArea.setVisible(false);


        return null;
    }

    public Void deleteButtonPressed(Integer id){

        try {
            facade.deleteMember(MemberList.stream().filter(Member -> Member.getId() == id).toList().get(0));
        } catch (Exception e) {
            message.setText("Error while deleting Member : delete error");
            System.err.println("Error while deleting Member : delete error");
            return null;
        }

        MemberList.removeIf(element -> element.getId() == id);
        listUpdate();
        return null;
    }






    /**
     *
     */
    public void handleMemberCreation() {
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

        Member Member = new Member(nameS, emailS, passwordS, false);

        boolean isCreated = false;
        try {
            isCreated = facade.createMember(Member);
        } catch (Exception e) {
            message.setText("Error while creating Member : connection error");
            return;
        }

        if (isCreated) {
            message.setText("Member created successfully !");
        } else {
            message.setText("Error while creating Member : Member not created");
        }

        MemberList.add(Member);
        listUpdate();

    }

    /**
     *
     */
    public void handleMemberUpdate() {
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

        // filter the list to get the Member with the id
        List<Member> filteredList = MemberList.stream().filter(Member -> Member.getId() == Integer.parseInt(id.getText())).toList();
        Member Member = filteredList.get(0);
        Member newMember = new Member(Member.getId(), Member.getUsername(), Member.getEmail(), Member.getPassword(), true);

        if (!nameS.isEmpty()) {
            newMember.setUsername(nameS);
        }

        if (!emailS.isEmpty()) {
            newMember.setEmail(emailS);
        }

        if (!passwordS.isEmpty()) {
            newMember.setPassword(passwordS, false);
        }

        boolean isUpdated = false;

        try {
            isUpdated = facade.updateMember(newMember);
        } catch (Exception e) {
            message.setText("Error while updating Member : connection error");
            return;
        }

        if (isUpdated) {
            message.setText("Member updated successfully");
        } else {
            message.setText("Error while updating Member : Member not updated");
            return;
        }

        MemberList.set(MemberList.indexOf(Member), newMember);
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