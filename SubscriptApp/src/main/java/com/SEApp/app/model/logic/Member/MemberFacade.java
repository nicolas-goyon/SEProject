package com.SEApp.app.model.logic.Member;

import com.SEApp.app.model.classes.Member;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
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
}
