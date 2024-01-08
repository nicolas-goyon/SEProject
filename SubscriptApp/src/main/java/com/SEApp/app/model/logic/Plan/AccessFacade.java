package com.SEApp.app.model.logic.Plan;

import com.SEApp.app.model.classes.Access;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.Dao.plans.AccessDao;

import java.sql.SQLException;
import java.util.List;

public class AccessFacade {

    private static AccessFacade instance;

    private AccessDao accessDao;

    private AccessFacade() throws SQLException {
        accessDao = AbstractDAOFactory.getInstance().getAccessDao();
    }

    public static AccessFacade getInstance() throws SQLException {
        if (instance == null) {
            instance = new AccessFacade();
        }
        return instance;
    }


    public Access createAccess(String name, String description) throws SQLException {
        Access access = new Access(name, description);
        return accessDao.create(access);
    }

    public Access getAccess(int id) throws SQLException {
        return accessDao.get(id);
    }

    public boolean updateAccess(Access access) throws SQLException, IncorrectOperandException {
        return accessDao.update(access).getId() != -1;
    }

    public boolean deleteAccess(Access access) throws SQLException, IncorrectOperandException {
        return accessDao.delete(access);
    }

    public List<Access> getAllAccesses() throws SQLException {
        return accessDao.list();
    }
}
