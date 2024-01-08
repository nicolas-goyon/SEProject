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

    private User user = null;

    public void setUser(User user) {
        if (this.user != null) {
            throw new RuntimeException("User already logged");
        }
        this.user = user;
    }

    public void login(User user) {
        setUser(user);
    }

    public User getUser() {
        return user;
    }

    public boolean isLogged() {
        return user != null;
    }

    public void logout() {
        user = null;
    }

    public boolean isAdmin() {
        return user instanceof Admin;
    }

    public boolean isManager() {
        return user instanceof Manager;
    }

    public boolean isMember() {
        return user instanceof Member;
    }



}

