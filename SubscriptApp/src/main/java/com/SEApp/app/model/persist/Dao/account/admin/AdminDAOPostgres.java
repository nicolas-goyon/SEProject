package com.SEApp.app.model.persist.Dao.account.admin;

import com.SEApp.app.model.classes.Admin;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.DBAccess.PostGres;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.SQLException;
import java.util.List;

public class AdminDAOPostgres extends AdminDAO {
    public AdminDAOPostgres(PostGres db) {
        super(db);
    }

    /**
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Admin get(int id) throws SQLException {
        throw new SQLException("Admin does not have an id");
    }

    /**
     * @param obj
     * @return
     * @throws SQLException
     */
    @Override
    public Admin create(Admin obj) throws SQLException {
        throw new SQLException("Cannot create an admin");
    }

    /**
     * @param obj
     * @return
     * @throws SQLException
     * @throws IncorrectOperandException
     */
    @Override
    public Admin update(Admin obj) throws SQLException, IncorrectOperandException {
        throw new SQLException("Cannot update an admin");
    }

    /**
     * @param obj
     * @return
     * @throws SQLException
     * @throws IncorrectOperandException
     */
    @Override
    public boolean delete(Admin obj) throws SQLException, IncorrectOperandException {
        throw new SQLException("Cannot delete an admin");
    }

    /**
     * @return
     * @throws SQLException
     */
    @Override
    public List<Admin> list() throws SQLException {
        throw new SQLException("Cannot list admins");
    }

    /**
     * @param username
     * @return
     * @throws Exception
     */
    @Override
    public Admin get(String username) throws SQLException {
        Admin admin = null;
        Dotenv dotenv = Dotenv.load();
        String admin_username = dotenv.get("ADMIN_LOGIN");
        if (!username.equals(admin_username)) {
            throw new SQLException("Admin does not exist");
        }

        String admin_password = dotenv.get("ADMIN_PASS");
        admin = new Admin(admin_username, admin_password, false);
        return admin;
    }
}
