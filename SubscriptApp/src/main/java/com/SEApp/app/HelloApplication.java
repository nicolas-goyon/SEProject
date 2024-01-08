package com.SEApp.app;

import com.github.fxrouter.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "Substrack");
        FXRouter.when("login", "view/login.fxml");
        FXRouter.when("managersManagement", "view/Managers.fxml");
        FXRouter.when("paymentTypesManagement", "view/PaymentTypeManagement.fxml");
        FXRouter.when("home", "view/home.fxml");
        FXRouter.when("register", "view/Register.fxml");
        FXRouter.when("planManagement", "view/PlanManagement.fxml");
        FXRouter.when("Member subscription", "view/MemberSubscription.fxml");
        FXRouter.when("Managerial Subscription", "view/ManagerialSubscription.fxml");
        FXRouter.when("Members list", "view/Members.fxml");
        FXRouter.when("accessManagement", "view/AccessManagement.fxml");
        try{
            FXRouter.goTo("home");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}