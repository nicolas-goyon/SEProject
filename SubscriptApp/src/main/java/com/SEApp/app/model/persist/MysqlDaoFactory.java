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
        try {
            this.userDao = new UserDaoMySQL(this.mysql.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this.userDao;
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