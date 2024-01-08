package com.SEApp.app.controller;

import com.SEApp.app.components.ElementLogic;
import com.SEApp.app.components.GridDisplay;
import com.SEApp.app.model.classes.Access;
import com.SEApp.app.model.classes.Plan;
import com.SEApp.app.model.logic.Plan.AccessFacade;
import com.SEApp.app.model.logic.Plan.PlanFacade;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanAccessManagementController {
    public ScrollPane planPane;
    public VBox accessesListArea;
    public Label message;

    private List<Integer> selectedAccessesIds = new ArrayList<>();

    private Plan selectedPlan;

    private List<Access> accesses;

    private List<Plan> plans;

    private PlanFacade planFacade;

    private AccessFacade accessFacade;

    private Map<String, String> options = new HashMap<>();

    public void initialize() {
        options.put("editText", "Select");
        options.put("noDelete", "true");
        options.put("maxWidth", "300");


        try {
            planFacade = PlanFacade.getInstance();
        } catch (Exception e) {
            raiseError("Error while initializing PlanFacade", e);
            return;
        }

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

        try {
            plans = planFacade.getPlanList();
        } catch (Exception e) {
            raiseError("Error while getting all plans", e);
            return;
        }

        updateDisplay();
    }

    private void updateDisplay() {
        updatePlanPane();
        updateAccessesListArea();
    }


    private Void callbackSelect(Integer id) {
        selectedPlan = getPlanFromListById(id);

        if (selectedPlan == null) {
            raiseError("Error while getting plan id can be incorrect : " + id);
            return null;
        }

        selectedAccessesIds.clear();
        for (Access access : selectedPlan.getAccesses()) {
            selectedAccessesIds.add(access.getId());
        }
        updateAccessesListArea();

        return null;
    }

    private void updateAccessesListArea() {
        accessesListArea.getChildren().clear();
        for (Access access : accesses) {
            CheckBox checkBox = new CheckBox(access.getName());
            checkBox.setUserData(access.getId());
            checkBox.setSelected(selectedAccessesIds.contains(access.getId()));
            checkBox.setOnAction(this::accessChange);
            accessesListArea.getChildren().add(checkBox);
        }
    }

    private void updatePlanPane() {
        List<ElementLogic> elements = new ArrayList<>();

        for (Plan plan : plans) {
            elements.add(new ElementLogic(plan.getId(), plan.getName(), plan.getDescription()));
        }
        GridDisplay gridDisplay = new GridDisplay(elements, this::callbackSelect, null, options);
        planPane.setContent(gridDisplay);
    }

    private Plan getPlanFromListById(Integer id) {
        return plans.stream().filter(plan -> plan.getId() == id).findFirst().orElse(null);
    }

    private void raiseError(String message) {
        this.message.setText(message);
        System.err.println(message);
    }

    private void raiseError(String message, Exception e) {
        raiseError(message);
        e.printStackTrace();
    }

    public void accessChange(ActionEvent actionEvent) {
        CheckBox checkBox = (CheckBox) actionEvent.getSource();
        int value = (int) checkBox.getUserData();
        if (checkBox.isSelected()) {
            selectedAccessesIds.add(value);
        } else {
            selectedAccessesIds.remove((Integer) value);
        }
    }

    public void save() {
        if (selectedPlan == null) {
            raiseError("You must select a plan");
            return;
        }

        if (selectedAccessesIds == null) {
            raiseError("Accesses list is null");
            return;
        }

        List<Access> selectedAccesses = new ArrayList<>();

        for (Integer selectedAccessId : selectedAccessesIds) {
            Access access = accesses.stream().filter(access1 -> access1.getId() == selectedAccessId).findFirst().orElse(null);
            if (access == null) {
                raiseError("Error while getting access id can be incorrect : " + selectedAccessId);
                return;
            }
            selectedAccesses.add(access);
        }

        selectedPlan.setAccesses(selectedAccesses);

        try {
            planFacade.updatePlan(selectedPlan);
        } catch (Exception e) {
            raiseError("Error while updating plan accesses", e);
            return;
        }

        message.setText("Plan accesses updated successfully");
    }
}
