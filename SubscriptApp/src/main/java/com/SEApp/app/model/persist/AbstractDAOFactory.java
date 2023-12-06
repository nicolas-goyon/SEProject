package com.SEApp.app.model.persist;

import com.SEApp.app.model.persist.account.UserDao;

/**
 * 
 */
public abstract class AbstractDAOFactory {
    /**
     *
     */
    private static AbstractDAOFactory instance = null;

    /**
     * 
     */
    public static AbstractDAOFactory getInstance() {
        if (instance == null) {
            instance = new MysqlDaoFactory();
        }
        return instance;
    }

    /**
     * 
     */
    public abstract UserDao getUserDao();

}