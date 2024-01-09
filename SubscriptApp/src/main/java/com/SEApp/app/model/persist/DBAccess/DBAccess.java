package com.SEApp.app.model.persist.DBAccess;

import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.utils.UpdateOperand;
import com.SEApp.app.model.persist.utils.WhereOperand;

import java.sql.SQLException;
import java.util.Map;

public interface DBAccess {

    @SuppressWarnings("rawtypes")
    public Map<String, Object>[] read(String table,String[] columns,WhereOperand[] whereOperands) throws SQLException;

    public <K> Map<String, Object> getByKey(String table, String[] columns, String keyColumn, K key) throws SQLException;

    public int create(String table, UpdateOperand[] values) throws SQLException;

    @SuppressWarnings("rawtypes")
    public int update(String table, UpdateOperand[] updateOperands, WhereOperand[] whereOperands) throws SQLException, IncorrectOperandException ;

    @SuppressWarnings("rawtypes")
    public int delete(String table, WhereOperand[] whereOperands) throws SQLException, IncorrectOperandException;


    public Map<String, Object>[] executeQuery(String query, Object[] values) throws SQLException ;


    }
