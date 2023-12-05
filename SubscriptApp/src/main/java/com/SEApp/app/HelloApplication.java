package com.SEApp.app;

import com.github.fxrouter.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "Hello Title", 300, 200);
        FXRouter.when("logged", "dashboard.fxml");
        FXRouter.when("mainPage", "hello-view.fxml");
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