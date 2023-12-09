package com.SEApp.app.model.business.Account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class UserFacadeTest {

    // TODO: This method should be deleted, it's for testing purposes only
    // TODO: This is an example of a test method with assertions
    @Test
    public void testDebugMethodWorking(){
        boolean res = UserFacade.debugMethod();
        Assertions.assertFalse(res);
    }


    @Test
    public void testUserFacadeGetInstanceWorking(){
        UserFacade userFacade = null;
        try {
            userFacade = UserFacade.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testRegisterIsImplemented() {
        UserFacade userFacade = null;
        try {
            userFacade = UserFacade.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Verify that the method is implemented
        userFacade.register("", "");
    }

}
