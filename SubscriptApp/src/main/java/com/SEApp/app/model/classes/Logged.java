package com.SEApp.app.model.classes;

public class Logged {
    private static Logged logged = null;

    private Logged() {
    }

    public static Logged getInstance() {
        if (logged == null) {
            logged = new Logged();
        }
        return logged;
    }


}
