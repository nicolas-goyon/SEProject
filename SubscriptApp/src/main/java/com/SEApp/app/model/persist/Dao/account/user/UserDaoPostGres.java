package com.SEApp.app.model.persist.Dao.account.user;

import com.SEApp.app.model.classes.User;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.DBAccess.PostGres;
import com.SEApp.app.model.persist.schemas.UserSchema;
import com.SEApp.app.model.persist.utils.WhereOperand;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 */
public class UserDaoPostGres extends UserDao {



    /**
     * Default constructor
     */
    public UserDaoPostGres(PostGres mysql){
        super(mysql);
    }

    @Override
    public User get(int id) throws SQLException {
        return getUserByKey(UserSchema.COLUMNS, UserSchema.ID, id);
    }

    private <K> User getUserByKey(String[] columns, String key, K value) throws SQLException {

        Map<String, Object> row = db.getByKey(UserSchema.TABLE, columns, key, value);


        if (row == null) {
            throw new SQLException("user not found");
        }


        return new User(
                (int) row.get(UserSchema.ID),
                (String) row.get(UserSchema.USERNAME),
                (String) row.get(UserSchema.EMAIL),
                (String) row.get(UserSchema.PASSWORD),
                (String) row.get(UserSchema.ROLE),
                (Integer) row.get(UserSchema.PLAN_ID),
                (Integer) row.get(UserSchema.PAYMENT_TYPE_ID),
                true
        );
    }

    @Override
    public User create(User user) throws SQLException {
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

        WhereOperand<Integer> whereOperand = new WhereOperand<>(UserSchema.ID, "=", obj.getId());
        @SuppressWarnings("rawtypes")
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

        WhereOperand<Integer> whereOperand = new WhereOperand<>(UserSchema.ID, "=", user.getId());
        @SuppressWarnings("rawtypes")
        WhereOperand[] whereOperands = {whereOperand};

        int affectedRows = db.delete(UserSchema.TABLE, whereOperands);
        if (affectedRows != 1) {
            throw new RuntimeException("failed to delete user (affected rows: " + affectedRows + ")");
        }

        return true;

    }

    @Override
    public List<User> list() throws SQLException {
        String[] columns = {UserSchema.ID, UserSchema.USERNAME, UserSchema.EMAIL, UserSchema.PASSWORD, UserSchema.ROLE};
        Map<String, Object>[] res = db.read(UserSchema.TABLE, columns, null);

        List<User> users = new ArrayList<>();
        for (Map<String, Object> row : res) {
            users.add(new User(
                    (int) row.get(UserSchema.ID),
                    (String) row.get(UserSchema.USERNAME),
                    (String) row.get(UserSchema.EMAIL),
                    (String) row.get(UserSchema.PASSWORD),
                    (String) row.get(UserSchema.ROLE),
                    (Integer) row.get(UserSchema.PLAN_ID),
                    (Integer) row.get(UserSchema.PAYMENT_TYPE_ID),
                    true
            ));
        }

        return users;
    }

    @Override
    public User findByEmail(String email) throws SQLException {
        return getUserByKey(UserSchema.COLUMNS, UserSchema.EMAIL, email);
    }

}