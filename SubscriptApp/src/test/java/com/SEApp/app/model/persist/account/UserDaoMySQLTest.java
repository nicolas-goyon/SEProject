package com.SEApp.app.model.persist.account;

import com.SEApp.app.model.persist.IncorrectOperandException;
import com.SEApp.app.model.persist.MySQL;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import com.SEApp.app.model.classes.User;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class UserDaoMySQLTest {

    @Test
    @Order(1)
    public void testCreateUser() {
        UserDaoMySQL userDaoMySQL = new UserDaoMySQL(new MySQL());
        User user = new User(-1, "test", "test@test", "test", "user", false);
        try {
            userDaoMySQL.save(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assert user.getId() != -1;
    }

    @Test
    @Order(2)
    public void testFindByEmail() {
        UserDaoMySQL userDaoMySQL = new UserDaoMySQL(new MySQL());
        User user = userDaoMySQL.findByEmail("test@test");
        assertEquals("test@test", user.getEmail());
    }

    @Test
    @Order(3)
    public void testUpdateUser() {
        UserDaoMySQL userDaoMySQL = new UserDaoMySQL(new MySQL());
        User user = userDaoMySQL.findByEmail("test@test");
        assertNotNull(user);
        System.out.printf("user: %s\n", user);
        user.setUsername("test2");
        try {
            userDaoMySQL.update(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IncorrectOperandException e) {
            throw new RuntimeException(e);
        }
        User user2 = userDaoMySQL.findByEmail("test@test");
        assertEquals("test2", user2.getUsername());
    }

    @Test
    @Order(4)
    public void testDeleteUser() {
        UserDaoMySQL userDaoMySQL = new UserDaoMySQL(new MySQL());
        User user = userDaoMySQL.findByEmail("test@test");
        assertNotNull(user);
        boolean res = true;
        try {
            res = userDaoMySQL.delete(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IncorrectOperandException e) {
            throw new RuntimeException(e);
        }

        assertTrue(res);

        User user2 = userDaoMySQL.findByEmail("test@test");
        assertNull(user2);
    }



}
