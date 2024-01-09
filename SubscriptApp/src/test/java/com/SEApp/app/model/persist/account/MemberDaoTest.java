package com.SEApp.app.model.persist.account;

import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.DBAccess.PostGres;
import com.SEApp.app.model.persist.Dao.Member.MemberDao;
import com.SEApp.app.model.persist.schemas.MemberSchema;
import org.junit.jupiter.api.*;
import com.SEApp.app.model.classes.Member;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberDaoTest {
    private static AbstractDAOFactory daoFactory = AbstractDAOFactory.getInstance();
    private static MemberDao memberDao;
    private static boolean connectionOk = false;
    private static boolean isFirst = true;

    @BeforeAll
    public static void initAll() {
        PostGres db = PostGres.getInstance();
        try {
            db.startBigTransaction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            memberDao = daoFactory.getMemberDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void endAll() {
        PostGres db = PostGres.getInstance();
        try {
            db.rollbackBigTransaction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void init() {
        if (!isFirst && !connectionOk) {
            fail("Connection failed");
        }
    }

    // test Connection to DB
    @Test
    @Order(0)
    public void testConnection() {
        isFirst = false;
        DBAccess db = daoFactory.getDBAccess();
        try {
            String table = MemberSchema.TABLE;
            String[] columns = {MemberSchema.ID};
            db.read(table, columns, null);
        } catch (Exception e) {
            fail("Connection failed " + e.getMessage());
        }
        connectionOk = true;
    }

    @Test
    @Order(1)
    public void testCreateMember() throws SQLException {
        Member member = new Member("test", "test@example.com", "AZERTYUIOP", true);
        memberDao.create(member);
        assert member.getId() != -1;
    }

    @Test
    @Order(2)
    public void testFindByEmail() throws SQLException {
        Member member = memberDao.findByEmail("test@example.com");
        if (member == null){
            List<Member> members = memberDao.list();
            for (Member m : members){
                System.out.println(m.getEmail());
            }
            fail("Member not found");
        }
        assertEquals("test@example.com", member.getEmail());
    }

    @Test
    @Order(3)
    public void testUpdateMember() throws SQLException, IncorrectOperandException {
        Member member = memberDao.findByEmail("test@example.com");
        assertNotNull(member);
        member.setUsername("updatedName");
        memberDao.update(member);
        Member updatedMember = memberDao.findByEmail("test@example.com");
        assertEquals("updatedName", updatedMember.getUsername());
    }

    @Test
    @Order(4)
    public void testDeleteMember() throws SQLException, IncorrectOperandException {
        Member member = memberDao.findByEmail("test@example.com");
        assertNotNull(member);
        boolean res = memberDao.delete(member);
        assertTrue(res);
        Member deletedMember = memberDao.findByEmail("test@example.com");
        assertNull(deletedMember);
    }
}
