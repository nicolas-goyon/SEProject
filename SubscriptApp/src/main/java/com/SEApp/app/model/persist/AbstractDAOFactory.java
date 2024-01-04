package com.SEApp.app.model.persist;

import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.Manager.ManagerDao;
import com.SEApp.app.model.persist.Dao.Member.MemberDao;
import com.SEApp.app.model.persist.Dao.account.paymentType.PaymentTypeDAO;
import com.SEApp.app.model.persist.Dao.account.user.UserDao;
import com.SEApp.app.model.persist.Dao.plans.PlanDao;


import java.sql.SQLException;

/**
 * 
 */
public abstract class AbstractDAOFactory {
    /**
     *
     */
    private static AbstractDAOFactory instance = null;

    /**
     * 
     */
    public static AbstractDAOFactory getInstance() {
        if (instance == null) {
            instance = new PostgresDaoFactory();
        }
        return instance;
    }

    public abstract DBAccess getDBAccess();

    /**
     * 
     */
    public abstract UserDao getUserDao() throws SQLException;

    public abstract PaymentTypeDAO getPaymentTypeDao() throws SQLException;

    public abstract ManagerDao getManagerDao() throws SQLException;

    public abstract PlanDao getPlanDao() throws SQLException;

    public abstract MemberDao getMemberDao() throws SQLException;

}