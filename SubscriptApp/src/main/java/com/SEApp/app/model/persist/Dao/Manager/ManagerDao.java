package com.SEApp.app.model.persist.Dao.Manager;

import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.Dao;
import com.SEApp.app.model.classes.Manager;
import com.SEApp.app.model.persist.DBAccess.MySQL;

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
    public abstract Manager findByEmail(String email);

    /**
     * @param id 
     * @return
     */
    public Manager get(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param obj 
     * @return
     */
    public Manager save(Manager obj) {
        // TODO implement here
        return null;
    }

    /**
     * @param obj 
     * @return
     */
    public Manager update(Manager obj) {
        // TODO implement here
        return null;
    }

    /**
     * @param obj 
     * @return
     */
    public boolean delete(Manager obj) {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public List<Manager> list() {
        // TODO implement here
        return null;
    }

}