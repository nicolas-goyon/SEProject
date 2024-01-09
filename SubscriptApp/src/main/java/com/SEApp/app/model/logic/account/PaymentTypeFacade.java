package com.SEApp.app.model.logic.account;

import com.SEApp.app.model.classes.PaymentType;
import com.SEApp.app.model.logic.Facade;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.AbstractDAOFactory;
import com.SEApp.app.model.persist.Dao.account.paymentType.PaymentTypeDAO;

import java.sql.SQLException;
import java.util.*;

/**
 * 
 */
public class PaymentTypeFacade implements Facade {

    private static final AbstractDAOFactory factory = AbstractDAOFactory.getInstance();
    private static PaymentTypeDAO paymentTypeDAO;

    /**
     * 
     */
    private PaymentTypeFacade()  {
        try {
            paymentTypeDAO = factory.getPaymentTypeDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static PaymentTypeFacade instance = null;

    public static PaymentTypeFacade getInstance() throws SQLException {
        if (instance == null) {
            instance = new PaymentTypeFacade();
        }
        return instance;
    }

    /**
     * @param paymentTypeID 
     * @return
     */
    public PaymentType getPaiementType(int paymentTypeID) throws SQLException {
        return paymentTypeDAO.get(paymentTypeID);
    }

    /**
     * @param paymentType
     * @return boolean
     */
    public boolean addPaiementType(PaymentType paymentType) throws SQLException {
        return paymentTypeDAO.create(paymentType) != null;
    }

    /**
     * @param paymentType 
     * @return
     */
    public boolean updatePaymentType(PaymentType paymentType) throws SQLException, IncorrectOperandException {
        return paymentTypeDAO.update(paymentType) != null;
    }

    /**
     * @param paymentTypeID 
     * @return
     */
    public Boolean deletePaymentType(PaymentType paymentTypeID) throws SQLException, IncorrectOperandException {
        return paymentTypeDAO.delete(paymentTypeID);
    }

    public List<PaymentType> getAllPaymentTypes() throws SQLException {
        return paymentTypeDAO.list();

    }
}