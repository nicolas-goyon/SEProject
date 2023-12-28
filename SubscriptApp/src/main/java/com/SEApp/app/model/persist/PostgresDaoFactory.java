package com.SEApp.app.model.persist;

import com.SEApp.app.model.persist.DBAccess.PostGres;
import com.SEApp.app.model.persist.Dao.Manager.ManagerDao;
import com.SEApp.app.model.persist.Dao.Manager.ManagerDaoPostGres;
import com.SEApp.app.model.persist.Dao.account.paymentType.PaymentTypeDAO;
import com.SEApp.app.model.persist.Dao.account.paymentType.PaymentTypeDAOPostgres;
import com.SEApp.app.model.persist.Dao.account.user.UserDao;
import com.SEApp.app.model.persist.Dao.account.user.UserDaoPostGres;

import java.sql.SQLException;

/**
 * 
 */
public class PostgresDaoFactory extends AbstractDAOFactory {

    /**
     *
     */
    private final PostGres postGres;


    /**
     *
     */
    public PostgresDaoFactory() {
        this.postGres = new PostGres();
    }

    /**
     * 
     */
    public UserDao getUserDao() throws SQLException {
        return new UserDaoPostGres(postGres);
    }

    /**
     * @return
     * @throws SQLException
     */
    @Override
    public PaymentTypeDAO getPaymentTypeDao() throws SQLException {
        return new PaymentTypeDAOPostgres(postGres);
    }

    public PostGres getDBAccess() {
        return postGres;
    }

    @Override
    public ManagerDao getManagerDao() throws SQLException {
        return new ManagerDaoPostGres(postGres);
    }

}