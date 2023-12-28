package com.SEApp.app.model.persist.Dao.account.user;

import com.SEApp.app.model.classes.User;
import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.Dao;

import java.sql.SQLException;

/**
 * 
 */
public abstract class UserDao extends Dao<User> {

    public UserDao( DBAccess db){
        super(db);
    }

    /**
     * @param email of user to get
     */
    public abstract User findByEmail(String email) throws SQLException;
}