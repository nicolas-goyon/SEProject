package com.SEApp.app.model.logic.Subscription;

import com.SEApp.app.model.classes.User;
import com.SEApp.app.model.logic.account.UserFacade;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.logic.exceptions.LoginException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.Dao.account.user.UserDao;

import java.sql.SQLException;

/**
 * 
 */
public class SubscriptionFacade {

    private static SubscriptionFacade instance;

    private static UserDao dao;

    /**
     * 
     */
    private SubscriptionFacade() {
        try {
            dao = AbstractDAOFactory.getInstance().getUserDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return
     */
    public static SubscriptionFacade getInstance() {
        if (instance == null) {
            instance = new SubscriptionFacade();
        }
        return instance;
    }


    /**
     * @param id 
     * @return
     */
    public boolean subscribe(int plan_id, int payment_type_id) throws SQLException, LoginException, IncorrectOperandException {
        UserFacade userFacade = UserFacade.getInstance();
        User user = userFacade.getCurrentUser();

        if(user == null) {
            throw new LoginException("You must be logged in to subscribe to a plan");
        }

        user.setPlan(plan_id);
        user.setPaymentType(payment_type_id);

        return dao.update(user) != null;

    }

    /**
     * @return
     */
    public boolean unsubscribe() throws LoginException, SQLException, IncorrectOperandException {
        UserFacade userFacade = UserFacade.getInstance();
        User user = userFacade.getCurrentUser();

        if(user == null) {
            throw new LoginException("You must be logged in to unsubscribe from a plan");
        }

        user.setPlan(null);

        return dao.update(user) != null;
    }

    public Integer getSubscription() throws LoginException, SQLException {
        UserFacade userFacade = UserFacade.getInstance();
        User user = userFacade.getCurrentUser();

        if(user == null) {
            throw new LoginException("You must be logged in to get your subscription");
        }

        return user.getPlan();
    }

    public boolean subscribeAUser(int plan_id, int payment_type_id, User user) throws SQLException, IncorrectOperandException {
        user.setPlan(plan_id);
        user.setPaymentType(payment_type_id);
        return dao.update(user) != null;
    }

}