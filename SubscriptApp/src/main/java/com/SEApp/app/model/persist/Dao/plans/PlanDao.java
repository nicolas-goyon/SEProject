package com.SEApp.app.model.persist.Dao.plans;

import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.Dao;
import com.SEApp.app.model.classes.Plan;

import java.sql.SQLException;

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


    public abstract Plan fillPlanAccesses(Plan plan) throws SQLException;
}