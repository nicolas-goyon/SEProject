package com.SEApp.app.model.persist.Dao.plans;

import com.SEApp.app.model.classes.Access;
import com.SEApp.app.model.classes.Plan;
import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.Dao;

/**
 * 
 */
public abstract class AccessDao extends Dao<Access> {

    /**
     * Default constructor
     */
    public AccessDao(DBAccess db) {
        super(db);
    }


}