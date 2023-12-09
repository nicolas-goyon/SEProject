package com.SEApp.app.model.logic.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class UserFacadeTest {



    @Test
    public void testUserFacadeGetInstanceWorking(){
        UserFacade userFacade = null;
        try {
            userFacade = UserFacade.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
