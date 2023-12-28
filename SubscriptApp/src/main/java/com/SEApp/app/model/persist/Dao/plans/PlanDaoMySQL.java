package com.SEApp.app.model.persist.Dao.plans;

import com.SEApp.app.model.classes.Plan;
import com.SEApp.app.model.persist.DBAccess.MySQL;

import java.util.*;

/**
 * 
 */
public class PlanDaoMySQL extends PlanDao {

    /**
     * Default constructor
     * @param db
     */
    public PlanDaoMySQL(MySQL db) {
        super(db);
    }


    /**
     * @param id
     * @return
     */
    @Override
    public Plan get(int id) {
        return null;
    }

    /**
     * @param plan 
     * @return
     */
    public Plan save(Plan plan) {
        // TODO implement here
        return null;
    }

    /**
     * @param plan 
     * @return
     */
    public Plan update(Plan plan) {
        // TODO implement here
        return null;
    }

    /**
     * @param plan 
     * @return
     */
    public boolean delete(Plan plan) {
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