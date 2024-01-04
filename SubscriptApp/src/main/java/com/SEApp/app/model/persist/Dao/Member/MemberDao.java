package com.SEApp.app.model.persist.Dao.Member;

import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.Dao;
import com.SEApp.app.model.classes.Member;

import java.sql.SQLException;

/**
 *
 */
public abstract class MemberDao extends Dao<Member> {

    /**
     * Default constructor
     */
    public MemberDao(DBAccess db) {
        super(db);
    }

    /**
     * @param email
     * @return
     */
    public abstract Member findByEmail(String email) throws SQLException;

}
