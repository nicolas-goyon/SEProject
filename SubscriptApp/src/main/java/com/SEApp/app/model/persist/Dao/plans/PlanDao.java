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


}