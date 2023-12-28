package com.SEApp.app.model.persist;

import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.DBAccess.MySQL;
import com.SEApp.app.model.persist.Dao.account.paymentType.PaymentTypeDAO;
import com.SEApp.app.model.persist.Dao.account.paymentType.PaymentTypeDAOMySQL;
import com.SEApp.app.model.persist.Dao.account.user.UserDao;
import com.SEApp.app.model.persist.Dao.account.user.UserDaoMySQL;

import java.sql.SQLException;

/**
 * 
 */
public class MysqlDaoFactory extends AbstractDAOFactory {

    /**
     * 
     */
    private final MySQL mysql;


    /**
     * 
     */
    public MysqlDaoFactory() {
        this.mysql = new MySQL();
    }

    /**
     * 
     */
    public UserDao getUserDao() throws SQLException {
        return new UserDaoMySQL(mysql);
    }

    public PaymentTypeDAO getPaymentTypeDao() throws SQLException {
        return new PaymentTypeDAOMySQL(mysql);
    }

    public DBAccess getDBAccess() {
        return mysql;
    }

}