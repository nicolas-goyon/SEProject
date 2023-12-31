package com.SEApp.app.controller;

import com.SEApp.app.components.ElementLogic;
import com.SEApp.app.components.GridDisplay;
import com.SEApp.app.model.classes.*;
import com.SEApp.app.model.logic.Plan.PlanFacade;
import com.SEApp.app.model.logic.Subscription.SubscriptionFacade;
import com.SEApp.app.model.logic.account.PaymentTypeFacade;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.sql.SQLException;
import java.util.*;

/**
 *
 */
public class ManagerialSubscriptionController {

    public Button subscribeButton;
    public Label subscriptionLabel;
    public Button unsubscribeButton;
    public ScrollPane paymentTypePane;
    public ScrollPane plansPane;
    public Label planLabel;
    public Label paymentTypeLabel;
    public Label memberLabel;

    private List<PaymentType> paymentTypes;
    private List<Plan> plans;

    private Plan selectedPlan;

    private PaymentType selectedPaymentType;

    private final Map<String, String> options = new HashMap<>();

    private Member selectedMember;

    public void initialize() {
        if (Logged.getInstance().getUser() == null || Logged.getInstance().getUser().getRole() != Role.MANAGER) {
            raiseError("You must be logged in as a manager manage a member's subscription");
            return;
        }

        if(!initializeSelectedMember()){
            return;
        }

        if(!initializeOptions()){
            return;
        }

        if(!initializePlansAndPayments()){
            return;
        }
        updatePlans();
        updatePaymentTypes();

        initializeSubscription();


    }

    private boolean initializeSelectedMember(){
        try {
            selectedMember = SubscriptionFacade.getInstance().getManagerialMember();
        } catch (Exception e) {
            raiseError("Could not get current member", e);
            return false;
        }

        if (selectedMember == null) {
            raiseError("Could not get the member");
            return false;
        }

        memberLabel.setText(selectedMember.getUsername());
        return true;
    }

    private boolean initializeOptions(){
        options.put("editText", "Select");
        options.put("noDelete", "true");
        options.put("maxWidth", "300");
        return true;
    }

    private boolean initializePlansAndPayments(){
        PlanFacade planFacade = null;
        PaymentTypeFacade paymentTypeFacade = null;

        try {
            planFacade = PlanFacade.getInstance();
            paymentTypeFacade = PaymentTypeFacade.getInstance();
        } catch (SQLException e) {
            raiseError("Could not connect to database", e);
            return false;
        }

        if (planFacade == null || paymentTypeFacade == null) {
            raiseError("Could not connect to database");
            return false;
        }

        try {
            paymentTypes = paymentTypeFacade.getAllPaymentTypes();
            plans = planFacade.getPlanList();
        } catch (SQLException e) {
            raiseError("Could not connect to database", e);
            return false;
        }

        if (paymentTypes == null || paymentTypes.isEmpty()) {
            raiseError("No payment types found");
            return false;
        }

        if (plans == null || plans.isEmpty()) {
            raiseError("No plans found");
            return false;
        }

        return true;
    }

    private void initializeSubscription() {

        Integer selectedPlanId = selectedMember.getPlan();
        Integer selectedPaymentTypeId = selectedMember.getPaymentType();

        if (selectedPlanId == null || selectedPaymentTypeId == null) {
            System.err.println("No plan or payment type found");
            setSubscriptionLabel(null, null);
            return;
        }

        Plan selectedPlan = plans.stream().filter(plan -> plan.getId() == selectedPlanId).findFirst().orElse(null);
        PaymentType selectedPaymentType = paymentTypes.stream().filter(paymentType -> paymentType.getId() == selectedPaymentTypeId).findFirst().orElse(null);

        this.selectedPlan = selectedPlan;
        this.selectedPaymentType = selectedPaymentType;

        setSubscriptionLabel(selectedPlan, selectedPaymentType);
    }

    private void updatePaymentTypes() {
        List<ElementLogic> paymentTypeElements = new ArrayList<>();
        paymentTypes.forEach(paymentType -> {
            paymentTypeElements.add(new ElementLogic(paymentType.getId(), paymentType.getName(), paymentType.getDescription()));
        });
        GridDisplay gridDisplay = new GridDisplay(paymentTypeElements, this::selectPaymentButton, null, options);
        paymentTypePane.setContent(gridDisplay);
    }

    private void updatePlans() {
        List<ElementLogic> planElements = new ArrayList<>();
        plans.forEach(plan -> {
            planElements.add(new ElementLogic(plan.getId(), plan.getName(), plan.getDescription()));
        });
        GridDisplay gridDisplay = new GridDisplay(planElements, this::selectPlanButton, null, options);
        plansPane.setContent(gridDisplay);
    }

    public Void selectPlanButton(Integer id) {
        Plan selectedPlan = plans.stream().filter(plan -> plan.getId() == id).findFirst().orElse(null);
        if (selectedPlan == null) {
            raiseError("Could not find plan with id " + id);
            return null;
        }
        planLabel.setText(selectedPlan.getName());
        this.selectedPlan = selectedPlan;
        return null;
    }

    public Void selectPaymentButton(Integer id) {
        PaymentType selectedPaymentType = paymentTypes.stream().filter(paymentType -> paymentType.getId() == id).findFirst().orElse(null);
        if (selectedPaymentType == null) {
            raiseError("Could not find payment type with id " + id);
            return null;
        }
        paymentTypeLabel.setText(selectedPaymentType.getName());
        this.selectedPaymentType = selectedPaymentType;
        return null;
    }


    public void subscribe() {
        if (selectedPlan == null) {
            raiseError("Please select a plan");
            return;
        }
        if (selectedPaymentType == null) {
            raiseError("Please select a payment type");
            return;
        }

        boolean success = false;
        try {
            success = SubscriptionFacade.getInstance().subscribeManagerialMember(selectedPlan.getId(), selectedPaymentType.getId());
            subscriptionLabel.setText("Successfully subscribed to " + selectedPlan.getName());
        } catch (SQLException e) {
            raiseError("Could not connect to database", e);
        } catch (Exception e) {
            raiseError("Could not subscribe to plan", e);
        }

        if (!success) {
            raiseError("Could not subscribe to plan");
        }

        setSubscriptionLabel(selectedPlan, selectedPaymentType);
    }

    private void setSubscriptionLabel(Plan plan, PaymentType paymentType) {
        if (plan == null) {
            subscriptionLabel.setText("Not subscribed to any plan");
            return;
        }
        if (paymentType == null) {
            subscriptionLabel.setText("Not subscribed to any payment type");
            return;
        }
        subscriptionLabel.setText("Currently subscribed to " + plan.getName() + " with " + paymentType.getName());
    }

    /**
     *
     */
    public void onUnsubscribe() {
        boolean success = false;
        try {
            success = SubscriptionFacade.getInstance().unsubscribeManagerialMember();
            subscriptionLabel.setText("Successfully unsubscribed");
        } catch (SQLException e) {
            raiseError("Could not connect to database", e);
        } catch (Exception e) {
            raiseError("Could not unsubscribe from plan", e);
        }

        if (!success) {
            raiseError("Could not unsubscribe from plan");
        }

        setSubscriptionLabel(null, null);
    }

    private void raiseError(String message) {
        subscriptionLabel.setText(message);
        System.err.println(message);
    }

    private void raiseError(String message, Exception e) {
        raiseError(message);
        e.printStackTrace();
    }
}