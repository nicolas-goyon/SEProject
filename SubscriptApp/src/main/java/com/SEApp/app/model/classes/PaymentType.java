package com.SEApp.app.model.classes;

import com.SEApp.app.model.persist.schemas.PaymentTypeSchema;
import com.SEApp.app.model.persist.utils.UpdateOperand;

import java.io.*;
import java.util.*;

/**
 *  PaymentType class
 *  Used to create PaymentType for users to be able to pay for subscriptions
 */
public class PaymentType implements Savable {


    /**
     * id of payment type
     */
    private int id;

    /**
     *  name of payment type
     */
    private String name;

    /**
     *  description of payment type
     */
    private String description;


    /**
     * @param id of payment type
     * @param name of payment type
     * @param description of payment type
     */
    public PaymentType( int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * @param name of payment type
     * @param description of payment type
     */
    public PaymentType( String name, String description) {
        this.id = -1;
        this.name = name;
        this.description = description;
    }


    /**
     * Default constructor
     */
    public PaymentType() {
        this.id = -1;
        this.name = "";
        this.description = "";
    }



    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id of payment type
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name of payment type
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description of payment type
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * @return the string representation of the payment type
     */
    @Override
    public UpdateOperand[] toUpdateOperands() {
        return new UpdateOperand[]{
                new UpdateOperand(PaymentTypeSchema.NAME, name),
                new UpdateOperand(PaymentTypeSchema.DESCRIPTION, description)
        };
    }
}