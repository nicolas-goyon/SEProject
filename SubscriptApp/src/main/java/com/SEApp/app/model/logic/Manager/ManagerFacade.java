package com.SEApp.app.model.logic.Manager;

import com.SEApp.app.model.classes.Manager;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.Dao.Manager.ManagerDao;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 */
public class ManagerFacade {


    /**
     * 
     */
    private static ManagerFacade instance;

    /**
     * 
     */
    private ManagerFacade() {}

    /**
     * @return
     */
    public static ManagerFacade getInstance() {
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
        AbstractDAOFactory factory = AbstractDAOFactory.getInstance();
        ManagerDao managerDao = factory.getManagerDao();
        return managerDao.save(manager).getId() != -1;
    }

    /**
     * @param manager to delete
     */
    public boolean deleteManager(Manager manager) throws SQLException, IncorrectOperandException {
        AbstractDAOFactory factory = AbstractDAOFactory.getInstance();
        ManagerDao managerDao = factory.getManagerDao();
        return managerDao.delete(manager);

    }

    /**
     * @param manager to update
     */
    public boolean updateManager(Manager manager) throws SQLException, IncorrectOperandException {
        AbstractDAOFactory factory = AbstractDAOFactory.getInstance();
        ManagerDao managerDao = factory.getManagerDao();
        return managerDao.update(manager).getId() != -1;
    }

    /**
     * @param id 
     * @return
     */
    public Manager getManager(long id) {
        // TODO implement here
        return null;
    }

    public List<Manager> getAllManagers() throws SQLException {
        AbstractDAOFactory factory = AbstractDAOFactory.getInstance();
        ManagerDao managerDao = factory.getManagerDao();
        return managerDao.list();
    }
}