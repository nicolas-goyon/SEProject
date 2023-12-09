package com.SEApp.app;

import com.github.fxrouter.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "Substrack");
        FXRouter.when("mainPage", "view/login.fxml");
        try{
            FXRouter.goTo("mainPage");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    public static void main(String[] args) {
        launch();
    }
}