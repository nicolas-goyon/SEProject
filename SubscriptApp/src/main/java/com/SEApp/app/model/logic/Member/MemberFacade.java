package com.SEApp.app.model.logic.Member;

import com.SEApp.app.model.classes.Member;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.logic.exceptions.LoginException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.Dao.Member.MemberDao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class MemberFacade {

    private static final AbstractDAOFactory factory = AbstractDAOFactory.getInstance();

    private static MemberDao memberDao;

    private static Member currentMember;


    /**
     *
     */
    private static MemberFacade instance;

    /**
     *
     */
    private MemberFacade() {
        try {
            memberDao = factory.getMemberDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return
     */
    public static MemberFacade getInstance() throws SQLException {
        if (instance == null) {
            instance = new MemberFacade();
        }
        return instance;
    }

    /**
     * @param member
     * @return
     */
    public boolean createMember(Member member) throws SQLException {
        return memberDao.create(member).getId() != -1;
    }

    /**
     * @param member to delete
     */
    public boolean deleteMember(Member member) throws SQLException, IncorrectOperandException {
        return memberDao.delete(member);

    }

    /**
     * @param member to update
     */
    public boolean updateMember(Member member) throws SQLException, IncorrectOperandException {
        return memberDao.update(member).getId() != -1;
    }

    /**
     * @param id
     * @return
     */
    public Member getMember(int id) throws SQLException {
        return memberDao.get(id);
    }

    public List<Member> getAllMembers() throws SQLException {
        return memberDao.list();
    }

    public static void setCurrentMember(Member member) {
        currentMember = member;
    }

    public static Member getCurrentMember() throws LoginException{
        if(currentMember == null) {
            throw new LoginException("You must be logged in to subscribe to a plan");
        }
        return currentMember;
    }

    public boolean login(String username, String password) throws SQLException, LoginException {
        Member member = memberDao.findByEmail(username);
        if(member == null) {
            throw new LoginException("Invalid username or password");
        }
        if(!member.checkPassword(password, false)) {
            throw new LoginException("Invalid username or password");
        }

        currentMember = member;
        return true;
    }

    public boolean register(Member member_to_register) throws SQLException, LoginException {
        String username = member_to_register.getUsername();
        String email = member_to_register.getEmail();


        Member member = memberDao.findByEmail(email);
        if(member != null) {
            throw new LoginException("Email already in use");
        }
        member = memberDao.findByUsername(username);
        if(member != null) {
            throw new LoginException("Username already in use");
        }


        return memberDao.create(member_to_register).getId() != -1;
    }

    public void logout() {
        currentMember = null;
    }

    public boolean isLogged() {
        return currentMember != null;
    }
}
