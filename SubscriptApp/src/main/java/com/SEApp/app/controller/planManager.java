package com.SEApp.app.controller;

import com.SEApp.app.components.ElementLogic;
import com.SEApp.app.components.GridDisplay;
import com.SEApp.app.model.classes.Plan;
import com.SEApp.app.model.logic.Plan.PlanFacade;
import com.github.fxrouter.FXRouter;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class planManager {


    /**
     * 
     */
    public List<Plan> planList;

    private static PlanFacade facade;


    /**
     * 
     */
    public ScrollPane displayPane;
    public VBox formModal;
    public Label id;
    public HBox idArea;
    public VBox nameArea;
    public TextField name;
    public VBox descriptionArea;
    public TextField description;
    public TextField price;
    public VBox priceArea;
    public Label message;


    public void initialize() {
        try {
            facade = PlanFacade.getInstance();
        } catch (Exception e) {
            raiseError("Error while loading plans : connection error");
            return;
        }
        if (facade == null) {
            raiseError("Error while loading plans : instance error");
            return;
        }
        try {
            planList = facade.getPlanList();
        } catch (Exception e) {
            raiseError("Error while loading plans : get error");
            return;
        }

        listUpdate();
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
        name.setText("");
        description.setText("");
        price.setText("");
        id.setText("New");
    }


    public void handleCreation() {

        Plan plan = getPlanFromForm();

        if (plan == null) {
            return;
        }

        boolean inserted = false;
        try {
            inserted = facade.createPlan(plan);
        } catch (Exception e) {
            raiseError("Error while adding plan : connection error", e);
            return;
        }

        if (!inserted) {
            raiseError("Error while adding plan : creation error");
            return;
        }

        planList.add(plan);
        listUpdate();
    }

    private void listUpdate() {
        List<ElementLogic> list = new ArrayList<>();
        for (Plan plan : planList) {
            list.add(new ElementLogic(plan.getId(), plan.getName(), plan.getDescription() + " - " + plan.getPrice() + "€"));
        }

        GridDisplay gridDisplay = new GridDisplay(list, this::editButtonClicked, this::deleteButtonClicked);
        displayPane.setContent(gridDisplay);
    }

    public Void editButtonClicked(Integer id) {
        Plan plan = planList.stream().filter(element -> element.getId() == id).findFirst().orElse(null);

        if (plan == null) {
            raiseError("Error while editing plan : plan not found");
            return null;
        }

        formModal.setVisible(true);
        this.id.setText(plan.getId() + "");
        name.setText(plan.getName());
        description.setText(plan.getDescription());
        price.setText(plan.getPrice() + "");

        return null;
    }

    public Void deleteButtonClicked(Integer id) {
        boolean deleted = false;
        try {
            deleted = facade.deletePlan(planList.stream().filter(element -> element.getId() == id).findFirst().orElse(null));
        } catch (Exception e) {
            raiseError("Error while deleting plan : connection error");
            return null;
        }

        if (!deleted) {
            raiseError("Error while deleting plan : delete error");
            return null;
        }

        planList.removeIf(element -> element.getId() == id);
        listUpdate();
        return null;
    }

    public void handleUpdate() {

        Plan plan = getPlanFromForm();

        if (plan == null) {
            return;
        }

        boolean updated = false;
        try {
            updated = facade.updatePlan(plan);
        } catch (Exception e) {
            raiseError("Error while updating plan : connection error");
            e.printStackTrace();
            return;
        }

        if (!updated) {
            raiseError("Error while updating plan : update error");
            return;
        }

        planList.removeIf(element -> element.getId() == plan.getId());
        planList.add(plan);
        listUpdate();
    }

    public void closeModal() {
        formModal.setVisible(false);
    }

    private void raiseError(String error) {
        message.setText(error);
        System.err.println(error);
    }

    private void raiseError(String error, Exception e) {
        raiseError(error);
        e.printStackTrace();
    }

    private Plan getPlanFromForm() {
        String idS = id.getText();
        String nameS = name.getText();
        String descriptionS = description.getText();
        String priceS = price.getText();

        if (nameS.isEmpty() || descriptionS.isEmpty() || priceS.isEmpty()) {
            message.setText("Please fill all fields");
            return null;
        }

        float priceF = 0;
        try {
            priceF = Float.parseFloat(priceS);
        } catch (Exception e) {
            message.setText("Please enter a valid price format");
            return null;
        }

        if (idS.equals("New")) {
            return new Plan(nameS, descriptionS, priceF, null);
        }

        int idI = 0;
        try {
            idI = Integer.parseInt(idS);
        } catch (Exception e) {
            message.setText("Please enter a valid id format");
            return null;
        }

        return new Plan(idI, nameS, descriptionS, priceF, null);
    }
}