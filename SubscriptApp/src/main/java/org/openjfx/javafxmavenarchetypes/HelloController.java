package org.openjfx.javafxmavenarchetypes;

import com.github.fxrouter.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Text longText;

    @FXML
    protected void onLogin(){
        //String chaine = "You are logged in";
        String chaine = "Your login is : "+ email.getText() + " and your password is " + password.getText();
        //longText.setText(chaine);

        try{
            FXRouter.goTo("logged", email.getText());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

}