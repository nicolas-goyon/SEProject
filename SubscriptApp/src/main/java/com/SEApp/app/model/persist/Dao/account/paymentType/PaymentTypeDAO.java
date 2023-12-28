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


}