package com.SEApp.app.model.persist.account;

import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.DBAccess.PostGres;
import com.SEApp.app.model.persist.Dao.Manager.ManagerDao;
import com.SEApp.app.model.persist.schemas.ManagerSchema;

import org.junit.jupiter.api.*;
import com.SEApp.app.model.classes.Manager;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class ManagerDaoTest {
    private static AbstractDAOFactory daoFactory = AbstractDAOFactory.getInstance();

    private static ManagerDao managerDao;


    private static boolean connectionOk = false;
    private static boolean isFirst = true;

    @BeforeAll
    public static void initAll(){
        PostGres db = PostGres.getInstance();
        try {
            db.startBigTransaction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            managerDao = daoFactory.getManagerDao();
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
            String table = ManagerSchema.TABLE;
            String[] columns = {ManagerSchema.ID};
            db.read(table, columns, null);
        } catch (Exception e) {
            fail("Connection failed " + e.getMessage());
        }

        connectionOk = true;
    }




    @Test
    @Order(1)
    public void testCreateManager() throws SQLException {
        Manager manager = new Manager(-1, "test", "test@test", "test", false);
        managerDao.create(manager);
        assert manager.getId() != -1;
    }

    @Test
    @Order(2)
    public void testFindByEmail() throws SQLException {
        Manager manager =  managerDao.findByEmail("test@test");
        assertEquals("test@test", manager.getEmail());
    }

    @Test
    @Order(3)
    public void testUpdateManager() throws SQLException, IncorrectOperandException {
        Manager manager =  managerDao.findByEmail("test@test");
        assertNotNull(manager);
        manager.setUsername("test2");
        managerDao.update(manager);
        Manager manager2 =  managerDao.findByEmail("test@test");
        assertEquals("test2", manager2.getUsername());
    }

    @Test
    @Order(4)
    public void testDeleteManager() throws SQLException, IncorrectOperandException {
        Manager manager =  managerDao.findByEmail("test@test");
        assertNotNull(manager);
        boolean res = managerDao.delete(manager);

        assertTrue(res);

        Manager manager2 =  managerDao.findByEmail("test@test");
        assertNull(manager2);
    }


}
