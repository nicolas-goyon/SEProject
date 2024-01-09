package com.SEApp.app.components;

import com.github.fxrouter.FXRouter;

import java.io.IOException;

public class Header {

    public void initialize() {}


    public void handleHomeButton() {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
