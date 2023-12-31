package com.SEApp.app.model.logic.Subscription;

import com.SEApp.app.model.classes.Access;
import com.SEApp.app.model.classes.Member;
import com.SEApp.app.model.classes.Plan;
import com.SEApp.app.model.logic.Facade;
import com.SEApp.app.model.logic.Member.MemberFacade;
import com.SEApp.app.model.logic.Plan.PlanFacade;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.logic.exceptions.LoginException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.Dao.Member.MemberDao;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 */
public class SubscriptionFacade implements Facade {


    private static SubscriptionFacade instance;

    private final MemberDao memberDao;

    private Member managerialMember;

    /**
     * 
     */
    private SubscriptionFacade() {
        try {
            memberDao = AbstractDAOFactory.getInstance().getMemberDao();
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

    public void setManagerialMember(Member managerialMember) {
        this.managerialMember = managerialMember;
    }

    public Member getManagerialMember() {
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

        member = memberDao.update(member);

        return member != null;

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

        member = memberDao.update(member);

        return member != null;
    }


    public boolean subscribeManagerialMember(int plan_id, int payment_type_id) throws SQLException, IncorrectOperandException {
        managerialMember.setPlan(plan_id);
        managerialMember.setPaymentType(payment_type_id);

        System.err.println("Name : " + managerialMember.getUsername() + " Plan : " + managerialMember.getPlan() + " Payment : " + managerialMember.getPaymentType());

        managerialMember = memberDao.update(managerialMember);

        return managerialMember != null;
    }

    public boolean unsubscribeManagerialMember() throws SQLException, IncorrectOperandException {
        managerialMember.setPlan(null);
        managerialMember.setPaymentType(null);

        System.err.println("Unsubscribing Name : " + managerialMember.getUsername());

        managerialMember = memberDao.update(managerialMember);

        return managerialMember != null;
    }

    public boolean checkAccess(int member_id, int access_id) throws SQLException {
        Member member = memberDao.get(member_id);

        if (member == null) {
            throw new SQLException("Member not found");
        }

        Plan plan = PlanFacade.getInstance().getPlan(member.getPlan());

        if (plan == null) {
            throw new SQLException("Plan not found");
        }

        List<Access> accesses = plan.getAccesses();

        if (accesses == null) {
            throw new SQLException("Accesses not found");
        }

        for (Access access : accesses) {
            if (access.getId() == access_id) {
                return true;
            }
        }

        return false;
    }


}