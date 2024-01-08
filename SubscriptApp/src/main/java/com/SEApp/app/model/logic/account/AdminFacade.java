package com.SEApp.app.model.logic.account;

import com.SEApp.app.model.classes.Admin;
import com.SEApp.app.model.classes.Logged;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.Dao.account.admin.AdminDAO;

import java.sql.SQLException;

public class AdminFacade {
    private static AdminFacade instance = null;

    private AdminFacade() {
    }

    public static AdminFacade getInstance() {
        if (instance == null) {
            instance = new AdminFacade();
        }
        return instance;
    }

    public boolean login(String username, String password) throws SQLException {
        AdminDAO adminDAO = AbstractDAOFactory.getInstance().getAdminDao();

        Admin admin = adminDAO.get(username);

        if (admin == null) {
            return false;
        }

        if (!admin.checkPassword(password, false)) {
            return false;
        }

        Logged.getInstance().login(admin);
        return true;
    }
}
