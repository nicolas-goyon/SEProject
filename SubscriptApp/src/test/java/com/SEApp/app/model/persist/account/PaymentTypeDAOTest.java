package com.SEApp.app.model.persist.account;

import com.SEApp.app.model.classes.PaymentType;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.DBAccess.PostGres;
import com.SEApp.app.model.persist.Dao.account.paymentType.PaymentTypeDAO;
import com.SEApp.app.model.persist.schemas.MemberSchema;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class PaymentTypeDAOTest {
    private static AbstractDAOFactory daoFactory = AbstractDAOFactory.getInstance();
    private static PaymentTypeDAO paymentTypeDAO;

    private static boolean connectionOk = false;
    private static boolean isFirst = true;

    private static int id = -1;


    @BeforeAll
    public static void initAll(){
        PostGres db = PostGres.getInstance();
        try {
            db.startBigTransaction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            paymentTypeDAO = daoFactory.getPaymentTypeDao();
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
    public void testCreatePaymentType() {
         
        PaymentType paymentType = new PaymentType("testName", "testDescription");
        try {
            paymentTypeDAO.create(paymentType);
        } catch ( SQLException e) {
            throw new RuntimeException(e);
        }
        id = paymentType.getId();
        assert id != -1;
    }

    @Test
    @Order(2)
    public void testFindById() throws SQLException {
        PaymentType paymentType = paymentTypeDAO.get(id);
        assert paymentType != null;
    }

    @Test
    @Order(3)
    public void testUpdatePaymentType() throws SQLException {
         
        PaymentType paymentType = paymentTypeDAO.get(id);
        paymentType.setName("testName2");
        try {
            paymentTypeDAO.update(paymentType);
        } catch (SQLException | IncorrectOperandException e) {
            throw new RuntimeException(e);
        }

        PaymentType paymentType2 = paymentTypeDAO.get(id);

        assert paymentType2.getName().equals("testName2");
    }

    @Test
    @Order(4)
    public void testDeletePaymentType() throws SQLException {
         
        PaymentType paymentType = paymentTypeDAO.get(id);
        try {
            paymentTypeDAO.delete(paymentType);
        } catch (SQLException | IncorrectOperandException e) {
            throw new RuntimeException(e);
        }
        PaymentType paymentType2 = paymentTypeDAO.get(id);

        assert paymentType2 == null;
    }
}
