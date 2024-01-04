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

    private static MemberDao MemberDao;


    /**
     *
     */
    private static MemberFacade instance;

    /**
     *
     */
    private MemberFacade() {
        try {
            MemberDao = factory.getMemberDao();
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
     * @param Member
     * @return
     */
    public boolean createMember(Member Member) throws SQLException {
        return MemberDao.create(Member).getId() != -1;
    }

    /**
     * @param Member to delete
     */
    public boolean deleteMember(Member Member) throws SQLException, IncorrectOperandException {
        return MemberDao.delete(Member);

    }

    /**
     * @param Member to update
     */
    public boolean updateMember(Member Member) throws SQLException, IncorrectOperandException {
        return MemberDao.update(Member).getId() != -1;
    }

    /**
     * @param id
     * @return
     */
    public Member getMember(int id) throws SQLException {
        return MemberDao.get(id);
    }

    public List<Member> getAllMembers() throws SQLException {
        return MemberDao.list();
    }
}
