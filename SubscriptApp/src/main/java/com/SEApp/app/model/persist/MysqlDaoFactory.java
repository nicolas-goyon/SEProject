package com.SEApp.app.model.persist;

import com.SEApp.app.model.persist.account.UserDao;
import com.SEApp.app.model.persist.account.UserDaoMySQL;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 */
public class MysqlDaoFactory extends AbstractDAOFactory {

    /**
     * 
     */
    private final MySQL mysql;


    /**
     * 
     */
    public MysqlDaoFactory() {
        this.mysql = new MySQL();
    }

    /**
     * 
     */
    public UserDao getUserDao() throws SQLException {
        return new UserDaoMySQL(mysql);
    }

}