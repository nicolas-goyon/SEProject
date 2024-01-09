package com.SEApp.app.model.persist.account;

import com.SEApp.app.model.classes.Admin;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.account.admin.AdminDAO;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class AdminDaoTest {
    private static final AbstractDAOFactory daoFactory = AbstractDAOFactory.getInstance();

    private static AdminDAO adminDAO;

    @BeforeAll
    public static void initAll() {
        DBAccess db = daoFactory.getDBAccess();
        try {
            db.startBigTransaction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            adminDAO = daoFactory.getAdminDao();
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

    @Test
    @Order(0)
    public void getAdmin() {

        Admin admin = null;
        try {
            admin = adminDAO.get("admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertNotNull(admin);
    }

    @Test
    @Order(1)
    public void getAdminByIdThrowsException() {
        Assertions.assertThrows(SQLException.class, () -> {
            adminDAO.get(0);
        });
    }

    @Test
    @Order(2)
    public void createAdminThrowsException() {
        Assertions.assertThrows(SQLException.class, () -> {
            adminDAO.create(new Admin("admin","password", false));
        });
    }

    @Test
    @Order(3)
    public void updateAdminThrowsException() {
        Assertions.assertThrows(SQLException.class, () -> {
            adminDAO.update(new Admin("admin","password", false));
        });
    }

    @Test
    @Order(4)
    public void deleteAdminThrowsException() {
        Assertions.assertThrows(SQLException.class, () -> {
            adminDAO.delete(new Admin("admin","password", false));
        });
    }
}
