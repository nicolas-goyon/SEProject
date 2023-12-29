package com.SEApp.app.model.logic.Manager;

import com.SEApp.app.model.classes.Manager;

/**
 * 
 */
public class ManagerFacade {


    /**
     * 
     */
    private ManagerFacade instance;

    /**
     * 
     */
    private ManagerFacade() {
        // TODO implement here
    }

    /**
     * @return
     */
    public static ManagerFacade getInstance() {
        // TODO implement here
        return null;
    }

    /**
     * @param manager
     * @return
     */
    public boolean createManager(Manager manager) {
        // TODO implement here
        return false;
    }

    /**
     * @param managerId
     */
    public boolean deleteManager(long managerId) {
        // TODO implement here
        return false;
    }

    /**
     * @param manager
     */
    public boolean updateManager(Manager manager) {
        // TODO implement here
        return false;
    }

    /**
     * @param id 
     * @return
     */
    public Manager getManager(long id) {
        // TODO implement here
        return null;
    }

}