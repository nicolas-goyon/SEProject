package com.SEApp.app.controller;

import com.SEApp.app.components.ElementLogic;
import com.SEApp.app.components.ListDisplay;
import com.SEApp.app.model.classes.PaymentType;
import com.SEApp.app.model.classes.Plan;
import com.SEApp.app.model.classes.User;
import com.SEApp.app.model.logic.Plan.PlanFacade;
import com.SEApp.app.model.logic.Subscription.SubscriptionFacade;
import com.SEApp.app.model.logic.account.PaymentTypeFacade;
import com.SEApp.app.model.logic.account.UserFacade;
import com.SEApp.app.model.logic.exceptions.LoginException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * 
 */
public class UserSubscriptionController {

    public Button subscribeButton;
    public Label subscriptionLabel;
    public Button unsubscribeButton;
    public ScrollPane paymentTypePane;
    public ScrollPane plansPane;
    public Label planLabel;
    public Label paymentTypeLabel;

    private List<PaymentType> paymentTypes;
    private List<Plan> plans;

    private Plan selectedPlan;

    private PaymentType selectedPaymentType;

    private final Map<String, String> options = new HashMap<>();

    public void initialize() {
        options.put("editText", "Select");
        options.put("noDelete", "true");
        options.put("maxWidth", "300");

        PlanFacade planFacade = null;
        PaymentTypeFacade paymentTypeFacade = null;

        try {
            planFacade = PlanFacade.getInstance();
            paymentTypeFacade = PaymentTypeFacade.getInstance();
        } catch (SQLException e) {
            raiseError("Could not connect to database", e);
        }

        if (planFacade == null || paymentTypeFacade == null) {
            raiseError("Could not connect to database");
            return;
        }

        try {
            paymentTypes = paymentTypeFacade.getAllPaymentTypes();
            plans = planFacade.getPlanList();
        } catch (SQLException e) {
            raiseError("Could not connect to database", e);
        }

        if(paymentTypes == null || paymentTypes.isEmpty()) {
            raiseError("No payment types found");
        } else {
            updatePaymentTypes();
        }

        if(plans == null || plans.isEmpty()) {
            raiseError("No plans found");
        } else {
            updatePlans();
        }

        initializeSubscription();


    }

    private void initializeSubscription(){
        User user = null;

        try {
            UserFacade userFacade = UserFacade.getInstance();
            user = userFacade.getCurrentUser();
        } catch (SQLException e) {
            raiseError("Could not connect to database", e);
        } catch (LoginException e) {
            raiseError("You must be logged in to subscribe to a plan", e);
        } catch (Exception e) {
            raiseError("Could not get current user", e);
        }

        if(user == null) {
            raiseError("You must be logged in to subscribe to a plan");
            return;
        }


        Integer selectedPlanId = user.getPlan();
        Integer selectedPaymentTypeId = user.getPaymentType();

        if (selectedPlanId == null || selectedPaymentTypeId == null) {
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
        ListDisplay listDisplay = new ListDisplay(paymentTypeElements, this::selectPaymentButton, null, options );
        paymentTypePane.setContent(listDisplay);
    }

    private void updatePlans() {
        List<ElementLogic> planElements = new ArrayList<>();
        plans.forEach(plan -> {
            planElements.add(new ElementLogic(plan.getId(), plan.getName(), plan.getDescription()));
        });
        ListDisplay listDisplay = new ListDisplay(planElements, this::selectPlanButton, null, options );
        plansPane.setContent(listDisplay);
    }

    public Void selectPlanButton(Integer id){
        Plan selectedPlan = plans.stream().filter(plan -> plan.getId() == id).findFirst().orElse(null);
        if(selectedPlan == null) {
            raiseError("Could not find plan with id " + id);
            return null;
        }
        planLabel.setText(selectedPlan.getName());
        this.selectedPlan = selectedPlan;
        return null;
    }

    public Void selectPaymentButton(Integer id){
        PaymentType selectedPaymentType = paymentTypes.stream().filter(paymentType -> paymentType.getId() == id).findFirst().orElse(null);
        if(selectedPaymentType == null) {
            raiseError("Could not find payment type with id " + id);
            return null;
        }
        paymentTypeLabel.setText(selectedPaymentType.getName());
        this.selectedPaymentType = selectedPaymentType;
        return null;
    }


    public void subscribe(){
        if(selectedPlan == null) {
            raiseError("Please select a plan");
            return;
        }
        if(selectedPaymentType == null) {
            raiseError("Please select a payment type");
            return;
        }

        boolean success = false;
        try {
            success = SubscriptionFacade.getInstance().subscribe(selectedPlan.getId(), selectedPaymentType.getId());
            subscriptionLabel.setText("Successfully subscribed to " + selectedPlan.getName());
        } catch (SQLException e) {
            raiseError("Could not connect to database", e);
        } catch (Exception e) {
            raiseError("Could not subscribe to plan", e);
        }

        if(!success) {
            raiseError("Could not subscribe to plan");
        }

        setSubscriptionLabel(selectedPlan, selectedPaymentType);
    }

    private void setSubscriptionLabel(Plan plan, PaymentType paymentType) {
        if(plan == null) {
            subscriptionLabel.setText("Not subscribed to any plan");
            return;
        }
        if(paymentType == null) {
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
            success = SubscriptionFacade.getInstance().unsubscribe();
            subscriptionLabel.setText("Successfully unsubscribed");
        } catch (SQLException e) {
            raiseError("Could not connect to database", e);
        } catch (Exception e) {
            raiseError("Could not unsubscribe from plan", e);
        }

        if(!success) {
            raiseError("Could not unsubscribe from plan");
        }

        setSubscriptionLabel(null, null);
    }

    private void raiseError(String message) {
        subscriptionLabel.setText(message);
        System.err.println(message);
    }

    private void raiseError(String message, Exception e) {
        subscriptionLabel.setText(message);
        System.err.println(message);
        e.printStackTrace();
    }

}