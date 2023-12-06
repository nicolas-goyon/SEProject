package com.SEApp.app.model.persist.account;

import com.SEApp.app.model.model.User;
import com.SEApp.app.model.persist.Dao;

import java.io.*;
import java.util.*;

/**
 * 
 */
public abstract class UserDao implements Dao<User> {

    /**
     * @param long id
     */
    public abstract User GetUser(long id);

    /**
     * 
     */
    public abstract User[] getAll();

    /**
     * @param User user
     */
    public abstract User save(User user);

    /**
     * @param user
     */
    public abstract User update(User user) ;
    /**
     * @param user
     */
    public abstract boolean delete(User user);


    /**
     * @param email
     */
    public abstract User findByEmail(String email);
}