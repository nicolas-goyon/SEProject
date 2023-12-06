package com.SEApp.app.model.persist.account;

import com.SEApp.app.model.model.User;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * 
 */
public class UserDaoMySQL extends UserDao {

    /**
     * Default constructor
     */
    public UserDaoMySQL( Connection connection) {
        this.sqlConnection = connection;
    }

    /**
     * 
     */
    private Connection sqlConnection;


    @Override
    public User GetUser(long id) {
        // TODO implement here
        throw new RuntimeException("not implemented");
    }

    @Override
    public User[] getAll() {
        // TODO implement here
        throw new RuntimeException("not implemented");
    }

    @Override
    public void save(User user) {
        // TODO implement here
        throw new RuntimeException("not implemented");
    }

    @Override
    public void update(User user, String[] params) {
        // TODO implement here
        throw new RuntimeException("not implemented");
    }

    @Override
    public void delete(User user) {
        // TODO implement here
        throw new RuntimeException("not implemented");
    }

    @Override
    public User findByEmail(String email) {
        // TODO implement here
        throw new RuntimeException("not implemented");
    }
}