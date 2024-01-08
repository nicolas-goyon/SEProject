package com.SEApp.app.model.logic.Subscription;

import com.SEApp.app.model.classes.Member;
import com.SEApp.app.model.logic.Member.MemberFacade;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.logic.exceptions.LoginException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.Dao.Member.MemberDao;

import java.sql.SQLException;

/**
 * 
 */
public class SubscriptionFacade {

    private static Member managerialMember;

    private static SubscriptionFacade instance;

    private static MemberDao dao;

    /**
     * 
     */
    private SubscriptionFacade() {
        try {
            dao = AbstractDAOFactory.getInstance().getMemberDao();
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

    public static void setManagerialMember(Member managerialMember) {
        SubscriptionFacade.managerialMember = managerialMember;
    }

    public static Member getManagerialMember() {
        return managerialMember;
    }


    /**
     * @param plan_id
     * @param payment_type_id
     * @return
     */
    public boolean subscribe(int plan_id, int payment_type_id) throws SQLException, LoginException, IncorrectOperandException {
        MemberFacade memberFacade = MemberFacade.getInstance();
        Member member = memberFacade.getCurrentMember();

        if(member == null) {
            throw new LoginException("You must be logged in to subscribe to a plan");
        }

        member.setPlan(plan_id);
        member.setPaymentType(payment_type_id);

        return dao.update(member) != null;

    }

    /**
     * @return
     */
    public boolean unsubscribe() throws LoginException, SQLException, IncorrectOperandException {
        MemberFacade memberFacade = MemberFacade.getInstance();
        Member member = memberFacade.getCurrentMember();

        if(member == null) {
            throw new LoginException("You must be logged in to unsubscribe from a plan");
        }

        member.setPlan(null);

        return dao.update(member) != null;
    }


    public boolean subscribeManagerialMember(int plan_id, int payment_type_id) throws SQLException, IncorrectOperandException {
        managerialMember.setPlan(plan_id);
        managerialMember.setPaymentType(payment_type_id);
        return dao.update(managerialMember) != null;
    }

    public boolean unsubscribeManagerialMember() throws SQLException, IncorrectOperandException {
        managerialMember.setPlan(null);
        return dao.update(managerialMember) != null;
    }


}