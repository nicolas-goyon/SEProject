package com.SEApp.app.model.logic.Manager;

import com.SEApp.app.model.classes.Logged;
import com.SEApp.app.model.classes.Manager;
import com.SEApp.app.model.logic.Facade;
import com.SEApp.app.model.logic.account.UserFacade;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.Dao.Manager.ManagerDao;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 */
public class ManagerFacade extends UserFacade {

    private static final AbstractDAOFactory factory = AbstractDAOFactory.getInstance();

    private static ManagerDao managerDao;


    /**
     * 
     */
    private static ManagerFacade instance;

    /**
     * 
     */
    private ManagerFacade() {
        try {
            managerDao = factory.getManagerDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return
     */
    public static ManagerFacade getInstance() throws SQLException {
        if (instance == null) {
            instance = new ManagerFacade();
        }
        return instance;
    }

    /**
     * @param manager
     * @return
     */
    public boolean createManager(Manager manager) throws SQLException {
        return managerDao.create(manager).getId() != -1;
    }

    /**
     * @param manager to delete
     */
    public boolean deleteManager(Manager manager) throws SQLException, IncorrectOperandException {
        return managerDao.delete(manager);

    }

    /**
     * @param manager to update
     */
    public boolean updateManager(Manager manager) throws SQLException, IncorrectOperandException {
        return managerDao.update(manager).getId() != -1;
    }

    /**
     * @param id 
     * @return
     */
    public Manager getManager(int id) throws SQLException {
        return managerDao.get(id);
    }

    public List<Manager> getAllManagers() throws SQLException {
        return managerDao.list();
    }

    public boolean login(String username, String password) throws SQLException {
        Manager manager = managerDao.findByUsername(username);
        if (manager == null) {
            return false;
        }
        if (!manager.checkPassword(password, false))
            return false;

        Logged.getInstance().setUser(manager);
        return true;
    }

}