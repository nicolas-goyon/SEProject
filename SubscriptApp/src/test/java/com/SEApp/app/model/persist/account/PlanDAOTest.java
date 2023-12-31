package com.SEApp.app.model.persist.account;

import com.SEApp.app.model.classes.Plan;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.plans.PlanDao;
import com.SEApp.app.model.persist.schemas.MemberSchema;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class PlanDAOTest {
    private static AbstractDAOFactory daoFactory = AbstractDAOFactory.getInstance();
    private static PlanDao planDao;

    private static boolean connectionOk = false;
    private static boolean isFirst = true;

    private static int id = -1;


    @BeforeAll
    public static void initAll(){
        DBAccess db = daoFactory.getDBAccess();
        try {
            db.startBigTransaction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            planDao = daoFactory.getPlanDao();
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
    public void testCreatePlan() throws SQLException {

        Plan plan = new Plan("testName", "testDescription", 10.0f, null);
        planDao.create(plan);
        id = plan.getId();
        assert id != -1;
    }

    @Test
    @Order(2)
    public void testFindById() throws SQLException {
        Plan plan = planDao.get(id);
        assert plan != null;
    }

    @Test
    @Order(3)
    public void testUpdatePlan() throws SQLException {

        Plan plan = planDao.get(id);
        plan.setName("testName2");
        try {
            planDao.update(plan);
        } catch (SQLException | IncorrectOperandException e) {
            throw new RuntimeException(e);
        }

        Plan plan2 = planDao.get(id);

        assert plan2.getName().equals("testName2");
    }

    @Test
    @Order(4)
    public void testDeletePlan() throws SQLException {

        Plan plan = planDao.get(id);
        try {
            planDao.delete(plan);
        } catch (SQLException | IncorrectOperandException e) {
            throw new RuntimeException(e);
        }
        Plan plan2 = planDao.get(id);

        assert plan2 == null;
    }
}
