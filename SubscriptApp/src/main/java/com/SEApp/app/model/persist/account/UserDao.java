package com.SEApp.app.model.persist.account;

import com.SEApp.app.model.model.User;

import java.io.*;
import java.util.*;

/**
 * 
 */
public abstract class UserDao {

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
    public abstract void save(User user);

    /**
     * @param User user 
     * @param String[] params
     */
    public abstract void update(User user, String[] params) ;
    /**
     * @param User user
     */
    public abstract void delete(User user);


    /**
     * @param String email
     */
    public abstract User findByEmail(String email);
}