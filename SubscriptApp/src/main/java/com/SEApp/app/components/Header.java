package com.SEApp.app.components;

import com.github.fxrouter.FXRouter;

import java.io.IOException;

public class Header {

    public void initialize() {
        // TODO implement here
        System.out.println("Header initialized");
    }


    public void handleHomeButton() {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
