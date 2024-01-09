package com.SEApp.app.model.persist.DBAccess;

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
public class PostGres implements DBAccess {


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

    private boolean inBigTransaction = false;


    /**
     *
     */
    private PostGres() {
        // load the environment variables
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("PSQL_URL");
        this.username = dotenv.get("PSQL_USER"); // TODO isn't needed because of azure connection string
        this.password = dotenv.get("PSQL_PASS");

        this.url = url.replace("{your_password_here}", this.password);

        if (this.url == null || this.username == null || this.password == null || this.url.isEmpty() || this.username.isEmpty() || this.password.isEmpty()) {
            throw new IllegalArgumentException("url, username and password must not be null");
        }
    }

    private static PostGres instance = null;

    public static PostGres getInstance() {
        if (instance == null) {
            instance = new PostGres();
        }

        return instance;
    }

    private Connection connection = null;

    /**
     * 
     */
    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    @Override
    public <K> Map<String, Object> getByKey(String table, String[] columns, String keyColumn, K key) throws SQLException {
        Connection connection = this.getConnection();

        StringBuilder query = select(columns);
        query.append(" FROM ").append(table);
        query.append(" WHERE ").append(keyColumn).append(" = ?;");

        PreparedStatement statement = connection.prepareStatement(query.toString()); // TODO prevent SQL injection
        if (key instanceof Integer) {
            statement.setInt(1, (Integer) key);
        } else if (key instanceof String) {
            statement.setString(1, (String) key);
        } else {
            // Handle other types as needed
            throw new IllegalArgumentException("Unsupported key type: " + key.getClass().getName());
        }
        ResultSet resultSet = statement.executeQuery();

        Map<String, Object>[] res = resultSetToMap(resultSet);

        close(connection, resultSet, statement);

        if (res.length == 0) {
            return null;
        }
        return res[0];
    }

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

        close(connection, null, statement);

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


        close(connection, generatedKeys, statement);

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

        close(connection, null, statement);

        return affectedRows;
    }



    public Map<String, Object>[] read(
            String table,
            String[] columns,
            WhereOperand[] whereOperands
    ) throws SQLException {
        Connection connection = this.getConnection();

        StringBuilder query = select(columns);
        query.append(" FROM ").append(table);
        if (whereOperands != null) {
            query.append(where(whereOperands));
        }
        query.append(";");

        PreparedStatement statement = connection.prepareStatement(query.toString()); // TODO prevent SQL injection
        if (whereOperands != null) {
            int i = 1;
            for (WhereOperand whereOperand : whereOperands) {
                statement.setObject(i, whereOperand.getValue());
                i++;
            }
        }
        ResultSet resultSet = statement.executeQuery();

        Map<String, Object>[] res = resultSetToMap(resultSet);

        close(connection, resultSet, statement);

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

        close(connection, resultSet, statement);

        return res;
    }

    public void startBigTransaction() throws SQLException {
        Connection connection = this.getConnection();
        connection.setAutoCommit(false);
        this.inBigTransaction = true;
    }

    public void endBigTransaction() throws SQLException {
        Connection connection = this.getConnection();
        connection.commit();
        connection.setAutoCommit(true);
        this.inBigTransaction = false;
    }

    public void rollbackBigTransaction() throws SQLException {
        Connection connection = this.getConnection();
        connection.rollback();
        connection.setAutoCommit(true);
        this.inBigTransaction = false;
    }

    private void close(Connection connection, ResultSet resultSet, Statement statement) throws SQLException {
        if(this.inBigTransaction) {
            return;
        }

        if(connection != null)
            connection.close();

        if(resultSet != null)
            resultSet.close();

        if(statement != null)
            statement.close();
    }
}