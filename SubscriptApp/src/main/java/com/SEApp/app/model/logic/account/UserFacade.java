package com.SEApp.app.model.logic.account;


import com.SEApp.app.model.classes.User;
import com.SEApp.app.model.logic.exceptions.LoginException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.utils.PasswordEncrypt;
import com.SEApp.app.model.persist.Dao.account.user.UserDao;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 */
public class UserFacade {

    /**
     * 
     */
    private static UserFacade instance;

    private final UserDao dao;

    /**
     * 
     */
    private User currentUser;

    private UserFacade() throws SQLException {
        AbstractDAOFactory factory = AbstractDAOFactory.getInstance();
        dao = factory.getUserDao();
    }

    public static UserFacade getInstance() throws SQLException {
        if (instance == null) {
            instance = new UserFacade();
        }
        return instance;
    }



    /**
     * @param user user to register
     */
    public boolean register(User user) throws SQLException {
        AbstractDAOFactory factory = AbstractDAOFactory.getInstance();
        UserDao userDao = factory.getUserDao();
        return userDao.create(user) != null;
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

    public void logout() {
        currentUser = null;
    }

    public boolean isLogged() {
        return currentUser != null;
    }

    public List<User> getAllUsers() throws SQLException {
        AbstractDAOFactory factory = AbstractDAOFactory.getInstance();
        UserDao userDao = factory.getUserDao();
        return userDao.list();
    }
}