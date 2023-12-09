package com.SEApp.app.model.persist.account;

import com.SEApp.app.model.classes.User;

import java.sql.Connection;
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
    public User get(long id) {
        // TODO implement here
        throw new RuntimeException("not implemented");
    }

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
    public User save(User user) {
        // TODO implement here
        throw new RuntimeException("not implemented");
    }

    @Override
    public User update(User obj) {
        // TODO implement here
        throw new RuntimeException("not implemented");
    }


    @Override
    public boolean delete(User user) {
        // TODO implement here
        throw new RuntimeException("not implemented");
    }

    @Override
    public List<User> list() {
        // TODO implement here
        throw new RuntimeException("not implemented");
    }

    @Override
    public User findByEmail(String email) {
        // TODO implement here
        throw new RuntimeException("not implemented");
    }
}