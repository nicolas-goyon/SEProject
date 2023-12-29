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
        FXRouter.when("paymentTypesManagement", "view/PaymentTypes.fxml");
        FXRouter.when("home", "view/home.fxml");
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