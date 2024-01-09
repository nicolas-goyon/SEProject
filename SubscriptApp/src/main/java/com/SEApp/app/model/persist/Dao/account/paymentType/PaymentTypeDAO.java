package com.SEApp.app.model.persist.Dao.account.paymentType;

import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.Dao;
import com.SEApp.app.model.classes.PaymentType;

/**
 * 
 */
public abstract class PaymentTypeDAO extends Dao<PaymentType> {

    public PaymentTypeDAO( DBAccess db){
        super(db);
    }


}