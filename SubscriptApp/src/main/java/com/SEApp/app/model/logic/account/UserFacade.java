package com.SEApp.app.model.logic.account;


import com.SEApp.app.model.classes.User;
import com.SEApp.app.model.logic.exceptions.LoginException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.utils.PasswordEncrypt;
import com.SEApp.app.model.persist.account.UserDao;

import java.sql.SQLException;

/**
 * 
 */
public class UserFacade {

    /**
     * 
     */
    private static UserFacade instance;

    /**
     * 
     */
    private User currentUser;

    private UserFacade() throws SQLException {
    }

    public static UserFacade getInstance() throws SQLException {
        if (instance == null) {
            instance = new UserFacade();
        }
        return instance;
    }



    /**
     * @param email email of the user
     * @param password password of the user (not encrypted)
     */
    public void register(String email, String password) { // TODO create a exception for null
        // TODO implement here
        throw new RuntimeException("not implemented");
    }

    /**
     * @param email email of the user
     * @param password password of the user (not encrypted)
     */
    public boolean login(String email, String password) throws SQLException {
        AbstractDAOFactory factory = AbstractDAOFactory.getInstance();
        UserDao userDao = factory.getUserDao();
        User user = userDao.findByEmail(email);

        if (user != null && PasswordEncrypt.checkPassword(password, user.getPassword())) {
            currentUser = user;
            return true;
        }
        return false;
    }



    public User getCurrentUser() throws LoginException {
        if (currentUser == null) {
            throw new LoginException("User not logged in");
        }
        return currentUser;
    }

}