package com.SEApp.app.model.persist;

import com.SEApp.app.model.persist.account.UserDao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 */
public class MysqlDaoFactory extends AbstractDAOFactory {

    /**
     * 
     */
    private MySQL mysql;

    /**
     * 
     */
    private UserDao userDao;

    /**
     * 
     */
    public MysqlDaoFactory() {
        this.mysql = new MySQL();
    }

    /**
     * 
     */
    public UserDao getUserDao() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public Connection getMySQLConnection() throws SQLException {
        mysql.getConnection();
        return mysql.getConnection();
    }

    /**
     * 
     */
    public void closeConnectionToDB() throws SQLException {
        mysql.closeConnection();
    }

}