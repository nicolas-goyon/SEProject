package com.SEApp.app.controller;

import com.SEApp.app.components.ElementLogic;
import com.SEApp.app.components.GridDisplay;
import com.SEApp.app.model.classes.PaymentType;
import com.SEApp.app.model.logic.account.PaymentTypeFacade;
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
public class PaymentTypeController {


    public ScrollPane displayPane;
    public VBox formModal;
    public HBox idArea;
    public TextField name;
    public VBox nameArea;
    public Label message;
    public VBox descriptionArea;

    public TextField description;

    public Label id;


    private List<PaymentType> paymentTypeList;

    private static PaymentTypeFacade facade;

    public void initialize() {

        try {
            facade = PaymentTypeFacade.getInstance();
        } catch (SQLException e) {
            message.setText("Error while loading payment types : connection error");
            System.err.println("Error while loading payment types : connection error");
            return;
        }
        if (facade == null) {
            message.setText("Error while loading payment types : instance error");
            System.err.println("Error while loading payment types : instance error");
            return;
        }
        try {
            paymentTypeList = facade.getAllPaymentTypes();
        } catch (Exception e) {
            message.setText("Error while loading payment types : get error");
            System.err.println("Error while loading payment types : get error");
            return;
        }

        listUpdate();

    }
    public void listUpdate(){
        List<ElementLogic> list = new ArrayList<>();

        for (PaymentType type : paymentTypeList) {
            list.add(new ElementLogic(type.getId(), type.getName(), type.getDescription()));
        }

        GridDisplay gridDisplay = new GridDisplay(list, this::editButtonPressed, this::deleteButtonPressed);
        displayPane.setContent(gridDisplay);
    }

    public Void editButtonPressed(Integer id){
        formModal.setVisible(true);

        PaymentType paymentType = paymentTypeList.stream().filter(p -> p.getId() == id).findFirst().orElse(null);

        if (paymentType == null) {
            message.setText("Error while loading payment type");
            System.err.println("Error while loading payment type");
            return null;
        }

        this.id.setText(String.valueOf(paymentType.getId()));
        name.setText(paymentType.getName());
        description.setText(paymentType.getDescription());

        return null;
    }

    public Void deleteButtonPressed(Integer id){

        boolean deleted = false;
        try {
            deleted = facade.deletePaymentType(paymentTypeList.stream().filter(p -> p.getId() == id).findFirst().orElse(null));
        } catch (Exception e) {
            message.setText("Error while deleting payment type : connection error");
            System.err.println("Error while deleting payment type : connection error");
            return null;
        }

        if (!deleted) {
            message.setText("Error while deleting payment type : delete error");
            System.err.println("Error while deleting payment type : delete error");
            return null;
        }

        paymentTypeList.removeIf(element -> element.getId() == id);
        listUpdate();
        return null;
    }


    public void handleNewButton(){
        formModal.setVisible(true);
        id.setText("New");
    }


    public void closeModal() {
        formModal.setVisible(false);
    }

    public void handleUpdate() {
        PaymentType paymentType = getPaymentTypeFromForm();


        if (paymentType == null) {
            return;
        }
        int id = Integer.parseInt(this.id.getText());
        paymentType.setId(id);

        boolean updated = false;
        try {
            updated = facade.updatePaymentType(paymentType);
        } catch (Exception e) {
            message.setText("Error while updating payment type : connection error");
            System.err.println("Error while updating payment type : connection error");
            return;
        }
        if (!updated) {
            message.setText("Error while updating payment type : update error");
            System.err.println("Error while updating payment type : update error");
            return;
        }

        paymentTypeList.removeIf(element -> element.getId() == paymentType.getId());
        paymentTypeList.add(paymentType);
        listUpdate();

    }

    public void handleCreation() {
        PaymentType paymentType = getPaymentTypeFromForm();

        if (paymentType == null) {
            return;
        }

        boolean inserted = false;
        try {
            inserted = facade.addPaiementType(paymentType);
        } catch (Exception e) {
            message.setText("Error while adding payment type : connection error");
            System.err.println("Error while adding payment type : connection error");
            return;
        }

        if (!inserted) {
            message.setText("Error while adding payment type : creation error");
            System.err.println("Error while adding payment type : creation error");
            return;
        }

        paymentTypeList.add(paymentType);
        listUpdate();
    }


    public PaymentType getPaymentTypeFromForm() {
        String nameS = name.getText();
        String descriptionS = description.getText();

        if (nameS.isEmpty() || descriptionS.isEmpty()) {
            message.setText("Please fill all fields");
            return null;
        }

        return new PaymentType(nameS, descriptionS);
    }

    public void handleHomeButton() {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}