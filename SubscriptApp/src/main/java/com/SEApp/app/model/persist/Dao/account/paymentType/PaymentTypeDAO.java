package com.SEApp.app.model.persist.Dao.account.paymentType;

import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.Dao;
import com.SEApp.app.model.classes.PaymentType;
import com.SEApp.app.model.persist.DBAccess.MySQL;

import java.sql.SQLException;
import java.util.*;

/**
 * 
 */
public abstract class PaymentTypeDAO extends Dao<PaymentType> {

    public PaymentTypeDAO( DBAccess db){
        super(db);
    }

    /**
     * @param id of user to get
     * @return the user
     */
    public abstract PaymentType get(int id) throws SQLException;

    /**
     * @param user to save
     */
    public abstract PaymentType save(PaymentType user) throws SQLException;

    /**
     * @param user to update
     */
    public abstract PaymentType update(PaymentType user) throws SQLException, IncorrectOperandException;
    /**
     * @param user to delete
     */
    public abstract boolean delete(PaymentType user) throws SQLException, IncorrectOperandException;

    public abstract List<PaymentType> list() throws SQLException;

}