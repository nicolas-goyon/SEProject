package com.SEApp.app.model.business.Account;


import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.account.UserDao;
import com.SEApp.app.model.model.User;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class UserFacade {

    /**
     * 
     */
    private static UserFacade instance;

    /**
     * 
     */
    private AbstractDAOFactory factory;

    /**
     * 
     */
    private UserDao userDao;

    /**
     * 
     */
    private User currentUser;

    private UserFacade() {
        factory = AbstractDAOFactory.getInstance();
        userDao = factory.getUserDao();
    }

    public static UserFacade getInstance() {
        if (instance == null) {
            instance = new UserFacade();
        }
        return instance;
    }



    /**
     * @param email email of the user
     * @param password password of the user (not encrypted)
     */
    public void register(String email, String password) { // TODO create a exception for null
        // TODO implement here
        throw new RuntimeException("not implemented");
    }

    /**
     * @param email email of the user
     * @param password password of the user (not encrypted)
     */
    public boolean login(String email, String password) {
        // TODO implement here
        throw new RuntimeException("not implemented");
    }

    public User getCurrentUser() { // TODO create a exception for null
        return currentUser;
    }

}