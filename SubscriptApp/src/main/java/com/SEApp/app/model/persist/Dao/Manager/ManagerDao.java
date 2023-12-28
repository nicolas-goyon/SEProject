package com.SEApp.app.model.persist.Dao.Manager;

import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.Dao;
import com.SEApp.app.model.classes.Manager;
import com.SEApp.app.model.persist.DBAccess.MySQL;

import java.sql.SQLException;
import java.util.*;

/**
 * 
 */
public abstract class ManagerDao extends Dao<Manager> {

    /**
     * Default constructor
     */
    public ManagerDao(DBAccess db) {
        super(db);
    }

    /**
     * @param email 
     * @return
     */
    public abstract Manager findByEmail(String email) throws SQLException;

    /**
     * @param id 
     * @return
     */
    public abstract Manager get(int id)throws SQLException;

    /**
     * @param obj 
     * @return
     */
    public abstract Manager save(Manager obj)throws SQLException;
    /**
     * @param obj 
     * @return
     */
    public abstract Manager update(Manager obj)throws SQLException, IncorrectOperandException;

    /**
     * @param obj 
     * @return
     */
    public abstract boolean delete(Manager obj)throws SQLException, IncorrectOperandException;


    /**
     * @return
     */
    public abstract List<Manager> list() throws SQLException;

}