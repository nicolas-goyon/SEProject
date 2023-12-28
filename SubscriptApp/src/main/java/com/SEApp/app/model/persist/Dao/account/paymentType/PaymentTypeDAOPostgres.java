package com.SEApp.app.model.persist.Dao.account.paymentType;

import com.SEApp.app.model.classes.PaymentType;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.DBAccess.PostGres;
import com.SEApp.app.model.persist.schemas.PaymentTypeSchema;
import com.SEApp.app.model.persist.utils.WhereOperand;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 */
public class PaymentTypeDAOPostgres extends PaymentTypeDAO {

    /**
     * @param db
     */
    public PaymentTypeDAOPostgres(PostGres db) {
        super(db);
    }

    /**
     * @param id of user to get
     * @return the user
     */
    @Override
    public PaymentType get(int id) throws SQLException {
        String[] columns =
        {
                PaymentTypeSchema.ID,
                PaymentTypeSchema.NAME,
                PaymentTypeSchema.DESCRIPTION
        };
        Map<String, Object> row = db.getByKey(PaymentTypeSchema.TABLE, columns, PaymentTypeSchema.ID, id);


        if (row == null) {
            return null;
        }

        return new PaymentType(
                (int) row.get(PaymentTypeSchema.ID),
                (String) row.get(PaymentTypeSchema.NAME),
                (String) row.get(PaymentTypeSchema.DESCRIPTION)
        );
    }

    /**
     * @param paymentType to save
     * @return the user
     * @throws SQLException
     */
    @Override
    public PaymentType save(PaymentType paymentType) throws SQLException {
        if (paymentType == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        if (paymentType.getId() != -1) {
            throw new IllegalArgumentException("user id must be -1, use update instead");
        }

        int insertedID = -1;
        insertedID = db.create(PaymentTypeSchema.TABLE, paymentType.toUpdateOperands());

        if (insertedID == -1) {
            throw new SQLException("error while inserting user");
        }
        paymentType.setId(insertedID);
        return paymentType;

    }

    /**
     * @param user
     * @return
     * @throws SQLException
     * @throws IncorrectOperandException
     */
    @Override
    public PaymentType update(PaymentType user) throws SQLException, IncorrectOperandException {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        if (user.getId() == -1) {
            throw new IllegalArgumentException("user id cannot be -1, use save instead");
        }

        WhereOperand whereOperand = new WhereOperand(PaymentTypeSchema.ID, "=", user.getId());
        WhereOperand[] whereOperands = {whereOperand};
        int updatedRows = db.update(PaymentTypeSchema.TABLE, user.toUpdateOperands(), whereOperands);

        if(updatedRows != 1)
            throw new SQLException("error while updating user with id " + user.getId() + ", affected rows: " + updatedRows);


        return user;
    }

    /**
     * @param user
     * @return
     * @throws SQLException
     * @throws IncorrectOperandException
     */
    @Override
    public boolean delete(PaymentType user) throws SQLException, IncorrectOperandException {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        if (user.getId() == -1) {
            throw new IllegalArgumentException("user id cannot be -1, use save instead");
        }

        WhereOperand whereOperand = new WhereOperand(PaymentTypeSchema.ID, "=",user.getId());
        WhereOperand[] whereOperands = {whereOperand};
        int deletedRows = db.delete(PaymentTypeSchema.TABLE, whereOperands);

        if(deletedRows != 1)
            throw new SQLException("error while deleting user with id " + user.getId() + ", affected rows: " + deletedRows);

        return true;
    }

    /**
     * @return
     * @throws SQLException
     */
    @Override
    public List<PaymentType> list() throws SQLException {
        List<PaymentType> res = new ArrayList<>();

        String[] columns =
        {
                PaymentTypeSchema.ID,
                PaymentTypeSchema.NAME,
                PaymentTypeSchema.DESCRIPTION
        };

        Map<String, Object>[] rows = db.read(PaymentTypeSchema.TABLE, columns, null);

        for (Map<String, Object> row : rows) {
            res.add(new PaymentType(
                    (int) row.get(PaymentTypeSchema.ID),
                    (String) row.get(PaymentTypeSchema.NAME),
                    (String) row.get(PaymentTypeSchema.DESCRIPTION)
            ));
        }

        return res;
    }


}