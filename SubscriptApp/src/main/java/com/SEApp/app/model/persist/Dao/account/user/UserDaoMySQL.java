package com.SEApp.app.model.persist.Dao.account.user;

import com.SEApp.app.model.classes.User;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.DBAccess.MySQL;
import com.SEApp.app.model.persist.utils.WhereOperand;
import com.SEApp.app.model.persist.schemas.UserSchema;

import java.sql.SQLException;
import java.util.*;

/**
 * 
 */
public class UserDaoMySQL extends UserDao {


    /**
     * Default constructor
     */
    public UserDaoMySQL(MySQL mysql){
        super(mysql);
    }

    @Override
    public User get(int id) throws SQLException {
        String[] columns = {UserSchema.USERNAME, UserSchema.EMAIL, UserSchema.PASSWORD, UserSchema.ROLE};
        WhereOperand whereOperand = new WhereOperand(UserSchema.ID, "=", id + "");
        WhereOperand[] whereOperands = {whereOperand};
        Map<String, Object>[] res = db.read(UserSchema.TABLE, columns, whereOperands);


        if (res == null || res.length == 0) {
            return null;
        }

        Map<String, Object> row = res[0];
        return new User(
                (int) row.get(UserSchema.ID),
                (String) row.get(UserSchema.USERNAME),
                (String) row.get(UserSchema.EMAIL),
                (String) row.get(UserSchema.PASSWORD),
                (String) row.get(UserSchema.ROLE),
                true
        );
    }

    @Override
    public User save(User user) throws SQLException {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        if (user.getId() != -1) {
            throw new IllegalArgumentException("user id must be -1, use update instead");
        }

        int insertedID = -1;
        insertedID = db.create(UserSchema.TABLE, user.toUpdateOperands());
        if (insertedID == -1) {
            throw new SQLException("failed to insert user");
        }

        user.setId(insertedID);

        return user;
    }

    @Override
    public User update(User obj) throws SQLException, IncorrectOperandException {
        if (obj == null) {
            throw new IllegalArgumentException("user cannot be null");
        }

        if (obj.getId() == -1) {
            throw new IllegalArgumentException("user id cannot be -1, use save instead");
        }

        WhereOperand whereOperand = new WhereOperand(UserSchema.ID, "=", obj.getId() + "");
        WhereOperand[] whereOperands = {whereOperand};

        int affectedRows = -1;
        affectedRows = db.update(UserSchema.TABLE, obj.toUpdateOperands(), whereOperands);
        if (affectedRows != 1) {
            throw new SQLException("failed to update user (affected rows: " + affectedRows + ")");
        }

        return obj;
    }

    @Override
    public boolean delete(User user) throws SQLException, IncorrectOperandException {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        if (user.getId() == -1) {
            throw new IllegalArgumentException("user id cannot be -1");
        }

        WhereOperand whereOperand = new WhereOperand(UserSchema.ID, "=", user.getId() + "");
        WhereOperand[] whereOperands = {whereOperand};

        int affectedRows = db.delete(UserSchema.TABLE, whereOperands);
        if (affectedRows != 1) {
            throw new RuntimeException("failed to delete user (affected rows: " + affectedRows + ")");
        }

        return true;

    }

    @Override
    public List<User> list() throws SQLException {
        String[] columns = {UserSchema.USERNAME, UserSchema.EMAIL, UserSchema.PASSWORD, UserSchema.ROLE};
        Map<String, Object>[] res = db.read(UserSchema.TABLE, columns, null);

        List<User> users = new ArrayList<>();
        for (Map<String, Object> row : res) {
            users.add(new User(
                    -1,
                    (String) row.get(UserSchema.USERNAME),
                    (String) row.get(UserSchema.EMAIL),
                    (String) row.get(UserSchema.PASSWORD),
                    (String) row.get(UserSchema.ROLE),
                    true
            ));
        }

        return users;
    }

    @Override
    public User findByEmail(String email) {
        String[] columns = {UserSchema.ID, UserSchema.USERNAME, UserSchema.EMAIL, UserSchema.PASSWORD, UserSchema.ROLE};
        WhereOperand whereOperand = new WhereOperand(UserSchema.EMAIL, "=", email);
        WhereOperand[] whereOperands = {whereOperand};
        Map<String, Object>[] res = null;
        try {
            res = db.read(UserSchema.TABLE, columns, whereOperands);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (res == null || res.length == 0) {
            return null;
        }

        Map<String, Object> row = res[0];

        Integer baseID = (Integer) row.get(UserSchema.ID);
        long id = baseID.longValue();
        return new User(
                (int) id,
                (String) row.get(UserSchema.USERNAME),
                (String) row.get(UserSchema.EMAIL),
                (String) row.get(UserSchema.PASSWORD),
                (String) row.get(UserSchema.ROLE),
                true
        );
    }

}