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

}