package com.SEApp.app.model.persist.Dao.account.paymentType;

import com.SEApp.app.model.persist.DBAccess.DBAccess;
import com.SEApp.app.model.persist.Dao.Dao;
import com.SEApp.app.model.classes.PaymentType;

/**
 *
 */
public abstract class PaymentDAO extends Dao<PaymentType> {

    public PaymentDAO( DBAccess db){
        super(db);
    }


}