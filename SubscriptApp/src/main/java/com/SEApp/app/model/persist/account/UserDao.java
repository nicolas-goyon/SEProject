package com.SEApp.app.model.persist.account;

import com.SEApp.app.model.classes.User;
import com.SEApp.app.model.persist.Dao;
import com.SEApp.app.model.persist.IncorrectOperandException;

import java.sql.SQLException;

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
    public abstract User[] getAll() throws SQLException;

    /**
     * @param User user
     */
    public abstract User save(User user) throws SQLException;

    /**
     * @param user
     */
    public abstract User update(User user) throws SQLException, IncorrectOperandException;
    /**
     * @param user
     */
    public abstract boolean delete(User user) throws SQLException, IncorrectOperandException;


    /**
     * @param email
     */
    public abstract User findByEmail(String email);
}