package com.SEApp.app.model.persist.Dao.plans;

import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.Dao;
import com.SEApp.app.model.classes.Plan;
import com.SEApp.app.model.persist.DBAccess.MySQL;

import java.util.*;

/**
 * 
 */
public abstract class PlanDao extends Dao<Plan> {

    /**
     * Default constructor
     */
    public PlanDao(DBAccess db) {
        super(db);
    }

    /**
     * @param id 
     * @return
     */
    public Plan get(long id) {
        // TODO implement here
        return null;
    }

    /**
     * @param obj 
     * @return
     */
    public Plan save(Plan obj) {
        // TODO implement here
        return null;
    }

    /**
     * @param obj 
     * @return
     */
    public Plan update(Plan obj) {
        // TODO implement here
        return null;
    }

    /**
     * @param obj 
     * @return
     */
    public boolean delete(Plan obj) {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public List<Plan> list() {
        // TODO implement here
        return null;
    }

}