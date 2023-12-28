package com.SEApp.app.model.persist.Dao.plans;

import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.Dao;
import com.SEApp.app.model.classes.Plan;

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


}