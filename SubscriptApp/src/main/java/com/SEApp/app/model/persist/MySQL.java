package com.SEApp.app.model.persist;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

/**
 *
 */
public class MySQL {

    /**
     * 
     */
    private final String url;

    /**
     * 
     */
    private final String username;

    /**
     * 
     */
    private final String password;

    /**
     *
     */
    private Connection connection;



    /**
     * 
     */
    public MySQL() {
        // load the environment variables
        Dotenv dotenv = Dotenv.load();
        this.url = dotenv.get("MYSQL_URL");
        this.username = dotenv.get("MYSQL_USER");
        this.password = dotenv.get("MYSQL_PASS");
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

    // TODO implement methods for CRUD
    public void create(
            String table,
            String[] columns,
            String[] values
    ) throws SQLException {
        String query = "INSERT INTO " + table + " (";
        for (int i = 0; i < columns.length; i++) {
            query += columns[i];
            if (i < columns.length - 1) {
                query += ", ";
            }
        }
        query += ") VALUES (";
        for (int i = 0; i < values.length; i++) {
            query += "'" + values[i] + "'";
            if (i < values.length - 1) {
                query += ", ";
            }
        }
        query += ");";
        System.out.println(query);
        this.connection.createStatement().executeUpdate(query);
    }

    public void update(
            String table,
            String[] columns,
            String[] values,
            String[] whereColumns,
            String[] whereValues
    ) throws SQLException {
        String query = "UPDATE " + table + " SET ";
        for (int i = 0; i < columns.length; i++) {
            query += columns[i] + " = '" + values[i] + "'";
            if (i < columns.length - 1) {
                query += ", ";
            }
        }
        query += " WHERE ";
        for (int i = 0; i < whereColumns.length; i++) {
            query += whereColumns[i] + " = '" + whereValues[i] + "'";
            if (i < whereColumns.length - 1) {
                query += " AND ";
            }
        }
        query += ";";
        System.out.println(query);
        this.connection.createStatement().executeUpdate(query);
    }

    public void delete(
            String table,
            String[] whereColumns,
            String[] whereValues
    ) throws SQLException {
        String query = "DELETE FROM " + table + " WHERE ";
        for (int i = 0; i < whereColumns.length; i++) {
            query += whereColumns[i] + " = '" + whereValues[i] + "'";
            if (i < whereColumns.length - 1) {
                query += " AND ";
            }
        }
        query += ";";
        System.out.println(query);
        this.connection.createStatement().executeUpdate(query);
    }

    public Object[] read(
            String table,
            String[] columns,
            String[] whereColumns,
            String[] whereValues
    ) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT ");
        for (int i = 0; i < columns.length; i++) {
            query.append(columns[i]);
            if (i < columns.length - 1) {
                query.append(", ");
            }
        }
        query.append(" FROM ").append(table).append(" WHERE ");
        for (int i = 0; i < whereColumns.length; i++) {
            query.append(whereColumns[i]).append(" = '").append(whereValues[i]).append("'");
            if (i < whereColumns.length - 1) {
                query.append(" AND ");
            }
        }
        query.append(";");
        System.out.println(query);
        ResultSet resultSet = this.connection.createStatement().executeQuery(query.toString());
        resultSet.next();
        Object[] result = new Object[columns.length];
        for (int i = 0; i < columns.length; i++) {
            result[i] = resultSet.getObject(columns[i]);
        }
        return result;
    }

    // TODO implement methods for complex queries

}