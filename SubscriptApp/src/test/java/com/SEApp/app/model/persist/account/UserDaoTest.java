package com.SEApp.app.model.persist.account;

import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.account.user.UserDao;
import com.SEApp.app.model.persist.schemas.MemberSchema;

import org.junit.jupiter.api.*;
import com.SEApp.app.model.classes.User;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class UserDaoTest {
    private static AbstractDAOFactory daoFactory = AbstractDAOFactory.getInstance();

    private static UserDao userDao;


    private static boolean connectionOk = false;
    private static boolean isFirst = true;

    @BeforeAll
    public static void initAll(){
        try {
             userDao = daoFactory.getUserDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @BeforeEach
    public void init(){
        if(!isFirst && !connectionOk){
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
    public void testCreateUser() throws SQLException {
        User user = new User(-1, "test", "test@test", "test", false);
        userDao.create(user);
        assert user.getId() != -1;
    }

    @Test
    @Order(2)
    public void testFindByEmail() throws SQLException {
        User user =  userDao.findByEmail("test@test");
        assertEquals("test@test", user.getEmail());
    }

    @Test
    @Order(3)
    public void testUpdateUser() throws SQLException, IncorrectOperandException {
        User user =  userDao.findByEmail("test@test");
        assertNotNull(user);
        user.setUsername("test2");
        userDao.update(user);
        User user2 =  userDao.findByEmail("test@test");
        assertEquals("test2", user2.getUsername());
    }

    @Test
    @Order(4)
    public void testDeleteUser() throws SQLException, IncorrectOperandException {
        User user =  userDao.findByEmail("test@test");
        assertNotNull(user);
        boolean res = userDao.delete(user);

        assertTrue(res);

        User user2 =  userDao.findByEmail("test@test");
        assertNull(user2);
    }


}
