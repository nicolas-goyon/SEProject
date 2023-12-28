package com.SEApp.app.model.persist.Dao.account.user;

import com.SEApp.app.model.classes.User;
import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.Dao;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.DBAccess.MySQL;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 */
public abstract class UserDao extends Dao<User> {

    public UserDao( DBAccess db){
        super(db);
    }


    /**
     * @param id of user to get
     * @return the user
     */
    public abstract User get(int id) throws SQLException;

    /**
     * @param user to save
     */
    public abstract User save(User user) throws SQLException;

    /**
     * @param user to update
     */
    public abstract User update(User user) throws SQLException, IncorrectOperandException;
    /**
     * @param user to delete
     */
    public abstract boolean delete(User user) throws SQLException, IncorrectOperandException;

    public abstract List<User> list() throws SQLException;

    /**
     * @param email of user to get
     */
    public abstract User findByEmail(String email) throws SQLException;
}