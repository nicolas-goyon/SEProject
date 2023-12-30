package com.SEApp.app.model.persist.Dao.Manager;

import com.SEApp.app.model.classes.Manager;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.DBAccess.PostGres;
import com.SEApp.app.model.persist.schemas.ManagerSchema;
import com.SEApp.app.model.persist.utils.WhereOperand;

import java.sql.SQLException;
import java.util.*;

/**
 * 
 */
public class ManagerDaoPostGres extends ManagerDao {


    /**
     * Default constructor
     * @param db to connect to
     */
    public ManagerDaoPostGres(PostGres db) {
        super(db);
    }

    /**
     * @param id  of user to get
     * @return the user
     */
    public Manager get(int id) throws SQLException {
        String[] columns = {ManagerSchema.USERNAME,  ManagerSchema.EMAIL,  ManagerSchema.PASSWORD};

        Map<String, Object> row = db.getByKey( ManagerSchema.TABLE, columns,  ManagerSchema.ID, id);

        if (row == null) {
            return null;
        }

        return new Manager(
                (int) row.get( ManagerSchema.ID),
                (String) row.get( ManagerSchema.USERNAME),
                (String) row.get( ManagerSchema.EMAIL),
                (String) row.get( ManagerSchema.PASSWORD),
                true
        );
    }

    @Override
    public  Manager create(Manager manager) throws SQLException {
        if (manager == null) {
            throw new IllegalArgumentException("manager cannot be null");
        }
        if (manager.getId() != -1) {
            throw new IllegalArgumentException("manager id must be -1, use update instead");
        }

        int insertedID = -1;
        insertedID = db.create( ManagerSchema.TABLE, manager.toUpdateOperands());
        if (insertedID == -1) {
            throw new SQLException("failed to insert manager");
        }

        manager.setId(insertedID);

        return manager;
    }

    @Override
    public  Manager update( Manager obj) throws SQLException, IncorrectOperandException {
        if (obj == null) {
            throw new IllegalArgumentException("manager cannot be null");
        }

        if (obj.getId() == -1) {
            throw new IllegalArgumentException("manager id cannot be -1, use save instead");
        }

        WhereOperand<Integer> whereOperand = new WhereOperand<Integer>( ManagerSchema.ID, "=", obj.getId());
        @SuppressWarnings("rawtypes")
        WhereOperand[] whereOperands = {whereOperand};

        int affectedRows = -1;
        affectedRows = db.update( ManagerSchema.TABLE, obj.toUpdateOperands(), whereOperands);

        if (affectedRows != 1) {
            throw new SQLException("failed to update manager (affected rows: " + affectedRows + ")");
        }

        return obj;
    }

    @Override
    public boolean delete( Manager manager) throws SQLException, IncorrectOperandException {
        if (manager == null) {
            throw new IllegalArgumentException("manager cannot be null");
        }
        if (manager.getId() == -1) {
            throw new IllegalArgumentException("manager id cannot be -1");
        }

        WhereOperand<Integer> whereOperand = new WhereOperand<Integer>( ManagerSchema.ID, "=", manager.getId());
        @SuppressWarnings("rawtypes")
        WhereOperand[] whereOperands = {whereOperand};

        int affectedRows = db.delete( ManagerSchema.TABLE, whereOperands);
        if (affectedRows != 1) {
            throw new RuntimeException("failed to delete manager (affected rows: " + affectedRows + ")");
        }

        return true;

    }

    @Override
    public List< Manager> list() throws SQLException {
        String[] columns = { ManagerSchema.ID,  ManagerSchema.USERNAME,  ManagerSchema.EMAIL,  ManagerSchema.PASSWORD};
        Map<String, Object>[] res = db.read( ManagerSchema.TABLE, columns, null);

        List< Manager> managers = new ArrayList<>();
        for (Map<String, Object> row : res) {
            managers.add(new  Manager(
                    (int) row.get( ManagerSchema.ID),
                    (String) row.get( ManagerSchema.USERNAME),
                    (String) row.get( ManagerSchema.EMAIL),
                    (String) row.get( ManagerSchema.PASSWORD),
                    true
            ));
        }

        return managers;
    }

    @Override
    public  Manager findByEmail(String email) throws SQLException {
        String[] columns = { ManagerSchema.ID,  ManagerSchema.USERNAME,  ManagerSchema.EMAIL,  ManagerSchema.PASSWORD};

        Map<String, Object> row = null;

        row = db.getByKey( ManagerSchema.TABLE, columns,  ManagerSchema.EMAIL, email);


        if (row == null) {
            return null;
        }

        return new  Manager(
                (int) row.get( ManagerSchema.ID),
                (String) row.get( ManagerSchema.USERNAME),
                (String) row.get( ManagerSchema.EMAIL),
                (String) row.get( ManagerSchema.PASSWORD),
                true
        );
    }

}