package com.SEApp.app.model.persist.Dao.Manager;

import com.SEApp.app.model.classes.Manager;
import com.SEApp.app.model.persist.DBAccess.MySQL;
import com.SEApp.app.model.persist.DBAccess.PostGres;

import java.util.*;

/**
 * 
 */
public class ManagerDaoPostGres extends ManagerDao {


    /**
     * Default constructor
     * @param db
     */
    public ManagerDaoPostGres(PostGres db) {
        super(db);
    }

    /**
     * @param id 
     * @return
     */
    public Manager get(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param manager 
     * @return
     */
    public Manager save(Manager manager) {
        // TODO implement here
        return null;
    }

    /**
     * @param manager 
     * @return
     */
    public Manager update(Manager manager) {
        // TODO implement here
        return null;
    }

    /**
     * @param manager 
     * @return
     */
    public boolean delete(Manager manager) {
        // TODO implement here
        return false;
    }

    /**
     * @param email 
     * @return
     */
    public Manager findByEmail(String email) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public List<Manager> list() {
        // TODO implement here
        return null;
    }

}