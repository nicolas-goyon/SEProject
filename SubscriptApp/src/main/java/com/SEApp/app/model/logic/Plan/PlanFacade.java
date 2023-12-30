package com.SEApp.app.model.logic.Plan;

import com.SEApp.app.model.classes.Plan;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.Dao.plans.PlanDao;

import java.sql.SQLException;
import java.util.*;

/**
 * 
 */
public class PlanFacade {

    private static PlanFacade facade;

    private static AbstractDAOFactory factory;

    private static PlanDao dao;

    /**
     * 
     */
    private PlanFacade() {
        factory = AbstractDAOFactory.getInstance();
        try {
            dao = factory.getPlanDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return
     */
    public static PlanFacade getInstance() {
        if (facade == null) {
            facade = new PlanFacade();
        }
        return facade;
    }

    /**
     * @return
     */
    public List<Plan> getPlanList() throws SQLException {
        return dao.list();
    }

    /**
     * @param id 
     * @return
     */
    public Plan getPlan(int id) throws SQLException {
        return dao.get(id);
    }

    /**
     * @param plan 
     * @return
     */
    public boolean createPlan(Plan plan) throws SQLException {
        return dao.create(plan) != null;
    }

    /**
     * @param plan 
     * @param
     */
    public boolean updatePlan(Plan plan) throws SQLException, IncorrectOperandException {
        return dao.update(plan) != null;
    }

    /**
     * @param plan
     * @return
     */
    public boolean deletePlan(Plan plan) throws SQLException, IncorrectOperandException {
        return dao.delete(plan);
    }

}