package com.SEApp.app.model.persist.Dao.account.admin;

import com.SEApp.app.model.classes.Admin;
import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.Dao;

import java.sql.SQLException;

public abstract class AdminDAO extends Dao<Admin> {

    public AdminDAO(DBAccess db){
        super(db);
    }

    public abstract Admin get(String username) throws SQLException;
}
