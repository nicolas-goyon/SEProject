package com.SEApp.app.model.persist;

import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.utils.UpdateOperand;
import com.SEApp.app.model.persist.utils.WhereOperand;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 */
public class MySQL {


    private String environment = "development";

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
    public MySQL() {
//        String envVar = System.getenv("ENVIRONMENT");
        // load the environment variables
        Dotenv dotenv = Dotenv.load();

        String url = "";
//        if (envVar != null) {
//            this.environment = envVar;
//
//            url = dotenv.get("TEST_MYSQL_URL");
//            this.username = dotenv.get("TEST_MYSQL_USER");
//            this.password = dotenv.get("TEST_MYSQL_PASS");
//        }
//        else {
//            url = dotenv.get("MYSQL_URL");
//            this.username = dotenv.get("MYSQL_USER");
//            this.password = dotenv.get("MYSQL_PASS");
//        }
        url = dotenv.get("MYSQL_URL");
        this.username = dotenv.get("MYSQL_USER");
        this.password = dotenv.get("MYSQL_PASS");
        url = url.replace("{your_password_here}", this.password);
        this.url = url.replace("{your_user_here}", this.username);

    }

    /**
     * 
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // TODO implement methods for CRUD

    public int delete(String table, WhereOperand[] whereOperands) throws SQLException, IncorrectOperandException {
        Connection connection = this.getConnection();

        StringBuilder query = new StringBuilder("DELETE FROM "); // TODO prevent SQL injection
        query.append(table).append(where(whereOperands));
        query.append(";");

        PreparedStatement statement = connection.prepareStatement(query.toString());
        int i = 1;
        for (WhereOperand whereOperand : whereOperands) {
            statement.setObject(i, whereOperand.getValue());
            i++;
        }
        int affectedRows = statement.executeUpdate();

        connection.close();
        statement.close();

        return affectedRows;
    }

    public int create(String table, UpdateOperand[] values) throws SQLException {
        Connection connection = this.getConnection();

        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(table).append(" (");
        for (UpdateOperand value : values) {
            query.append(value.getColumn()).append(", ");
        }
        query.delete(query.length() - 2, query.length());
        query.append(") VALUES (");
        for (UpdateOperand value : values) {
            query.append("?, ");
        }
        query.delete(query.length() - 2, query.length());
        query.append(");");

        PreparedStatement statement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS); // TODO prevent SQL injection
        int i = 1;
        for (UpdateOperand value : values) {
            statement.setObject(i, value.getValue());
            i++;
        }
        // res = id of the created row
        int affectedRows = statement.executeUpdate();


        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        ResultSet generatedKeys = statement.getGeneratedKeys();
        int res = -1;
        if (generatedKeys.next()) {
            res = generatedKeys.getInt(1);
        } else {
            throw new SQLException("Creating user failed, no ID obtained.");
        }


        connection.close();
        statement.close();

        return res;
    }

    public int update(String table, UpdateOperand[] updateOperands, WhereOperand[] whereOperands) throws SQLException, IncorrectOperandException {
        Connection connection = this.getConnection();

        StringBuilder query = new StringBuilder("UPDATE "); // TODO prevent SQL injection
        query.append(table).append(set(updateOperands));
        query.append(where(whereOperands));
        query.append(";");

        PreparedStatement statement = connection.prepareStatement(query.toString());
        int i = 1;
        for (UpdateOperand updateOperand : updateOperands) {
            statement.setObject(i, updateOperand.getValue());
            i++;
        }
        for (WhereOperand whereOperand : whereOperands) {
            statement.setObject(i, whereOperand.getValue());
            i++;
        }

        int affectedRows = statement.executeUpdate();

        connection.close();
        statement.close();

        return affectedRows;
    }



    public Map<String, Object>[] read(
            String table,
            String[] columns,
            WhereOperand[] whereOperands
    ) throws SQLException {
        Connection connection = this.getConnection();

        StringBuilder query = select(columns);
        query.append(" FROM ").append(table).append(where(whereOperands));
        query.append(";");

        PreparedStatement statement = connection.prepareStatement(query.toString()); // TODO prevent SQL injection
        int i = 1;
        for (WhereOperand whereOperand : whereOperands) {
            statement.setObject(i, whereOperand.getValue());
            i++;
        }
        ResultSet resultSet = statement.executeQuery();

        Map<String, Object>[] res = resultSetToMap(resultSet);

        connection.close();
        resultSet.close();
        statement.close();

        return res;
    }

    private Map<String, Object>[] resultSetToMap(ResultSet resultSet) throws SQLException {
        ArrayList<Map<String, Object>> resultList = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, Object> row = new java.util.HashMap<>();
            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                row.put(resultSet.getMetaData().getColumnName(i + 1), resultSet.getObject(i + 1));
            }
            resultList.add(row);
        }
        @SuppressWarnings("unchecked")
        Map<String, Object>[] res = new Map[resultList.size()];
        res = resultList.toArray(res);
        return res;
    }

    private StringBuilder select(String[] columns) {
        StringBuilder query = new StringBuilder("SELECT ");
        for (int i = 0; i < columns.length; i++) {
            query.append(columns[i]);
            if (i < columns.length - 1) {
                query.append(", ");
            }
        }
        return query;
    }

    private StringBuilder set(UpdateOperand[] updateOperands) throws IncorrectOperandException {
        if (updateOperands == null || updateOperands.length == 0) {
            throw new IncorrectOperandException("updateOperands must not be null or empty");
        }

        StringBuilder query = new StringBuilder(" SET ");
        for (int i = 0; i < updateOperands.length; i++) {
            query.append(updateOperands[i].toPreparedString());
            if (i < updateOperands.length - 1) {
                query.append(", ");
            }
        }
        return query;
    }

    private StringBuilder where(WhereOperand[] whereOperands) {
        if (whereOperands == null) {
            return new StringBuilder();
        }

        StringBuilder query = new StringBuilder(" WHERE ");
        for (int i = 0; i < whereOperands.length; i++) {
            query.append(whereOperands[i].toPreparedString());
            if (i < whereOperands.length - 1) {
                query.append(" AND ");
            }
        }
        return query;
    }

    // TODO implement methods for complex queries

    public Map<String, Object>[] executeQuery(String query, Object[] values) throws SQLException {
        Connection connection = this.getConnection();

        PreparedStatement statement = connection.prepareStatement(query); // TODO prevent SQL injection
        int i = 1;
        for (Object value : values) {
            statement.setObject(i, value);
            i++;
        }
        ResultSet resultSet = statement.executeQuery();

        Map<String, Object>[] res = resultSetToMap(resultSet);

        connection.close();
        resultSet.close();
        statement.close();

        return res;
    }
}