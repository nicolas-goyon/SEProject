package com.SEApp.app.model.classes;

import com.SEApp.app.model.persist.schemas.PlanSchema;
import com.SEApp.app.model.persist.utils.UpdateOperand;

import java.io.*;
import java.util.*;

/**
 *  Plan class
 *  This class is used to create a plans for users to subscribe to.
 *  Plans are created by managers.
 *  Plans are linked to one or more access types.
 */
public class Plan implements Savable {

    /**
     *  id of plan
     */
    public int id;

    /**
     * name of plan
     */
    private String name;

    /**
     *  description of plan
     */
    private String description;

    /**
     *  price of plan
     */
    private float price;

    /**
     *  access types of plan
     */
    private List<Access> accesses;

    /**
     * @param id of plan
     * @param name of plan
     * @param Description of plan
     * @param price of plan
     * @param accesses of plan
     */
    public Plan(int id, String name, String Description, float price, List<Access> accesses) {
        this.id = id;
        this.name = name;
        this.description = Description;
        this.price = price;
        this.accesses = accesses;
    }

    /**
     * Default constructor
     */
    public Plan() {
        this.id = -1;
        this.name = "";
        this.description = "";
        this.price = 0.0f;
        this.accesses = null;
    }

    /**
     * @param name of plan
     * @param Description of plan
     * @param price of plan
     * @param accesses of plan
     */
    public Plan(String name, String Description, float price, List<Access> accesses) {
        this.id = -1;
        this.name = name;
        this.description = Description;
        this.price = price;
        this.accesses = accesses;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id of plan
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
     * @param name of plan
     */
    public void setName(String name) {
        this.name= name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description of plan
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price of plan
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the accesses
     */
    public List<Access> getAccesses() {
        return accesses;
    }

    /**
     * @param accesses of plan
     */
    public void setAccesses(List<Access> accesses) {
        this.accesses = accesses;
    }

    /**
     * @return
     */
    @Override
    public UpdateOperand[] toUpdateOperands() {
        return new UpdateOperand[]{
                new UpdateOperand(PlanSchema.NAME, name),
                new UpdateOperand(PlanSchema.DESCRIPTION, description),
                new UpdateOperand(PlanSchema.PRICE, price)
        };
    }
}