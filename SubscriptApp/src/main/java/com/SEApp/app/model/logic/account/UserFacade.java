package com.SEApp.app.model.logic.account;

import com.SEApp.app.model.classes.Logged;
import com.SEApp.app.model.classes.User;
import com.SEApp.app.model.logic.Facade;
import com.SEApp.app.model.logic.exceptions.LoginException;

import java.sql.SQLException;

public abstract class UserFacade implements Facade {
    public abstract boolean login(String username, String password) throws SQLException, LoginException;

    public void logout() {
        Logged.getInstance().logout();
    }


    public boolean isLogged() {
        return Logged.getInstance().isLogged();
    }

    public User getLoggedUser() {
        return Logged.getInstance().getUser();
    }
}
