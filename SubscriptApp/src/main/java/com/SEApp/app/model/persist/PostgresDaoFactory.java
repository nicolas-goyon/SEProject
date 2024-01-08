package com.SEApp.app.model.persist;

import com.SEApp.app.model.persist.DBAccess.PostGres;
import com.SEApp.app.model.persist.Dao.Manager.ManagerDao;
import com.SEApp.app.model.persist.Dao.Manager.ManagerDaoPostGres;
import com.SEApp.app.model.persist.Dao.Member.MemberDao;
import com.SEApp.app.model.persist.Dao.Member.MemberDaoPostGres;
import com.SEApp.app.model.persist.Dao.account.admin.AdminDAO;
import com.SEApp.app.model.persist.Dao.account.admin.AdminDAOPostgres;
import com.SEApp.app.model.persist.Dao.account.paymentType.PaymentTypeDAO;
import com.SEApp.app.model.persist.Dao.account.paymentType.PaymentTypeDAOPostgres;
import com.SEApp.app.model.persist.Dao.plans.PlanDao;
import com.SEApp.app.model.persist.Dao.plans.PlanDaoPostGres;

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

    @Override
    public PlanDao getPlanDao() throws SQLException {
        return new PlanDaoPostGres(postGres);
    }

    @Override
    public MemberDao getMemberDao() throws SQLException {
        return new MemberDaoPostGres(postGres);
    }

    @Override
    public AdminDAO getAdminDao() throws SQLException {
        return new AdminDAOPostgres(postGres);
    }
}