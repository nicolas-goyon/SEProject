package com.SEApp.app.model.persist.account;

import com.SEApp.app.model.classes.Access;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.plans.AccessDao;
import com.SEApp.app.model.persist.schemas.AccessSchema;
import com.SEApp.app.model.persist.schemas.ManagerSchema;

import org.junit.jupiter.api.*;
import com.SEApp.app.model.classes.Manager;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class AccessDaoTest {
    private static AbstractDAOFactory daoFactory = AbstractDAOFactory.getInstance();

    private static AccessDao dao;


    private static boolean connectionOk = false;
    private static boolean isFirst = true;

    @BeforeAll
    public static void initAll(){
        DBAccess db = daoFactory.getDBAccess();
        try {
            db.startBigTransaction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            dao = daoFactory.getAccessDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void endAll() {
        DBAccess db = daoFactory.getDBAccess();
        try {
            db.rollbackBigTransaction();
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
            String table = AccessSchema.TABLE;
            String[] columns = {AccessSchema.ID};
            db.read(table, columns, null);
        } catch (Exception e) {
            fail("Connection failed " + e.getMessage());
        }

        connectionOk = true;
    }




    @Test
    @Order(1)
    public void testCreateManager() throws SQLException {
        Access access = new Access("test name", "test description");
        dao.create(access);
        assert access.getId() != -1;
    }

    @Test
    @Order(2)
    public void testGet() throws SQLException {
        Access access = dao.get(1);
        assertNotNull(access);
    }

    @Test
    @Order(3)
    public void testUpdateAccess() throws SQLException, IncorrectOperandException {
        Access access = dao.get(1);
        assertNotNull(access);
        access.setName("test name 2");
        dao.update(access);
        Access access2 = dao.get(1);
        assertEquals("test name 2", access2.getName());
    }

    @Test
    @Order(4)
    public void testDeleteAccess() throws SQLException, IncorrectOperandException {
        Access access = dao.get(1);
        assertNotNull(access);
        dao.delete(access);
        Access access2 = dao.get(1);
        assertNull(access2);
    }


}
