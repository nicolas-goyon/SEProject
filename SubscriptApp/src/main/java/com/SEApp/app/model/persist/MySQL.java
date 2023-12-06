package com.SEApp.app.model.persist;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 * 
 */
public class MySQL {

    /**
     * 
     */
    private String url;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String password;

    /**
     *
     */
    private Connection connection;



    /**
     * 
     */
    public MySQL() {
        // TODO implement here
        throw  new RuntimeException("MySQL not initialized");
    }

    /**
     * 
     */
    public void closeConnection() throws SQLException {
        this.connection.close();
    }

    /**
     * 
     */
    public Connection getConnection() throws SQLException {
        this.connection = DriverManager.getConnection(url, username, password);
        return this.connection;
    }

}