package com.SEApp.app.model.persist.Dao.plans;

import com.SEApp.app.model.classes.Access;
import com.SEApp.app.model.classes.Plan;
import com.SEApp.app.model.logic.Plan.AccessFacade;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.DBAccess.PostGres;
import com.SEApp.app.model.persist.schemas.AccessSchema;
import com.SEApp.app.model.persist.schemas.PlanAccessSchema;
import com.SEApp.app.model.persist.schemas.PlanSchema;
import com.SEApp.app.model.persist.utils.UpdateOperand;
import com.SEApp.app.model.persist.utils.WhereOperand;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

/**
 * 
 */
public class PlanDaoPostGres extends PlanDao {

    /**
     * Default constructor
     * @param db
     */
    public PlanDaoPostGres(PostGres db) {
        super(db);
    }


    /**
     * @param id  of user to get
     * @return the user
     */
    public Plan get(int id) throws SQLException {
        String[] columns = {PlanSchema.ID, PlanSchema.NAME, PlanSchema.DESCRIPTION, PlanSchema.PRICE};

        Map<String, Object> row = db.getByKey(PlanSchema.TABLE, columns, PlanSchema.ID, id);

        if (row == null) {
            return null;
        }

        BigDecimal priceBigDec = (BigDecimal) row.get(PlanSchema.PRICE);
        float price = priceBigDec.floatValue();

        Plan plan = new Plan(
                (int) row.get(PlanSchema.ID),
                (String) row.get(PlanSchema.NAME),
                (String) row.get(PlanSchema.DESCRIPTION),
                price,
                null
        );

        fillPlanAccesses(plan);

        return plan;
    }

    @Override
    public Plan create(Plan plan) throws SQLException {
        if (plan == null) {
            throw new IllegalArgumentException("plan cannot be null");
        }
        if (plan.getId() != -1) {
            throw new IllegalArgumentException("plan id must be -1, use update instead");
        }

        int insertedID = -1;
        insertedID = db.create(PlanSchema.TABLE, plan.toUpdateOperands());
        if (insertedID == -1) {
            throw new SQLException("failed to insert plan");
        }

        plan.setId(insertedID);

        // insert plan accesses
        try {
            updatePlanAccesses(plan);
        } catch (IncorrectOperandException e) {
            throw new SQLException("failed to insert plan accesses, incorrect operand");
        }

        return plan;
    }

    @Override
    public Plan update(Plan obj) throws SQLException, IncorrectOperandException {
        if (obj == null) {
            throw new IllegalArgumentException("plan cannot be null");
        }

        if (obj.getId() == -1) {
            throw new IllegalArgumentException("plan id cannot be -1, use save instead");
        }

        WhereOperand<Integer> whereOperand = new WhereOperand<Integer>(PlanSchema.ID, "=", obj.getId());
        @SuppressWarnings("rawtypes")
        WhereOperand[] whereOperands = {whereOperand};

        int affectedRows = db.update(PlanSchema.TABLE, obj.toUpdateOperands(), whereOperands);

        if (affectedRows != 1) {
            throw new SQLException("failed to update plan (affected rows: " + affectedRows + ")");
        }

        // update plan accesses
        updatePlanAccesses(obj);

        return obj;
    }

    @Override
    public boolean delete(Plan plan) throws SQLException, IncorrectOperandException {
        if (plan == null) {
            throw new IllegalArgumentException("plan cannot be null");
        }
        if (plan.getId() == -1) {
            throw new IllegalArgumentException("plan id cannot be -1");
        }

        // delete all accesses from plan_accesses where plan_id = plan.getId()
        deletePlanAccesses(plan);

        // delete plan
        WhereOperand<Integer> whereOperand = new WhereOperand<Integer>(PlanSchema.ID, "=", plan.getId());
        @SuppressWarnings("rawtypes")
        WhereOperand[] whereOperands = {whereOperand};

        int affectedRows = db.delete(PlanSchema.TABLE, whereOperands);
        if (affectedRows != 1) {
            throw new RuntimeException("failed to delete plan (affected rows: " + affectedRows + ")");
        }

        return true;

    }

    @Override
    public List<Plan> list() throws SQLException {
        String[] columns = {PlanSchema.ID, PlanSchema.NAME, PlanSchema.DESCRIPTION, PlanSchema.PRICE};
        Map<String, Object>[] res = db.read(PlanSchema.TABLE, columns, null);

        List<Plan> plans = new ArrayList<>();



        for (Map<String, Object> row : res) {

            BigDecimal priceBigDec = (BigDecimal) row.get(PlanSchema.PRICE);
            float price = priceBigDec.floatValue();

            Plan plan = new Plan(
                    (int) row.get(PlanSchema.ID),
                    (String) row.get(PlanSchema.NAME),
                    (String) row.get(PlanSchema.DESCRIPTION),
                    price,
                    null
            );

            fillPlanAccesses(plan);

            plans.add(plan);
        }

        return plans;
    }

    /**
     * @param plan
     * @return
     */
    @Override
    public Plan fillPlanAccesses(Plan plan) throws SQLException {
        // table plan_accesses : plan_id, access_id

        String[] columnsAccessId = {PlanAccessSchema.ACCESS_ID};

        WhereOperand<Integer> whereOperand = new WhereOperand<Integer>(PlanAccessSchema.PLAN_ID, "=", plan.getId());

        @SuppressWarnings("rawtypes")
        WhereOperand[] whereOperands = {whereOperand};

        Map<String, Object>[] res = db.read(PlanAccessSchema.TABLE, columnsAccessId, whereOperands);

        List<Integer> accessIds = new ArrayList<>();

        for (Map<String, Object> row : res) {
            accessIds.add((int) row.get(PlanAccessSchema.ACCESS_ID));
        }

        List<Access> accesses = new ArrayList<>();

        AccessFacade accessFacade = AccessFacade.getInstance();

        for (Integer accessId : accessIds) {
            Access access = accessFacade.getAccess(accessId);
            accesses.add(access);
        }

        plan.setAccesses(accesses);

        return plan;
    }

    private void updatePlanAccesses(Plan plan) throws SQLException, IncorrectOperandException {
        // delete all accesses from plan_accesses where plan_id = plan.getId()
        deletePlanAccesses(plan);

        // insert all accesses from plan.getAccesses() into plan_accesses
        if(plan.getAccesses() == null) return;


        for (Access access : plan.getAccesses()) {
            UpdateOperand<Integer> planIdOperand = new UpdateOperand<Integer>(PlanAccessSchema.PLAN_ID, plan.getId());
            UpdateOperand<Integer> accessIdOperand = new UpdateOperand<Integer>(PlanAccessSchema.ACCESS_ID, access.getId());

            @SuppressWarnings("rawtypes")
            UpdateOperand[] row = {planIdOperand, accessIdOperand};

            int affectedRows = db.create(PlanAccessSchema.TABLE, row);

            if (affectedRows == -1) {
                throw new SQLException("failed to insert plan accesses");
            }
        }
    }

    private void deletePlanAccesses(Plan plan) throws SQLException, IncorrectOperandException {
        // remove all accesses from plan_accesses where plan_id = plan.getId()

        WhereOperand<Integer> whereOperand = new WhereOperand<Integer>(PlanAccessSchema.PLAN_ID, "=", plan.getId());

        @SuppressWarnings("rawtypes")
        WhereOperand[] whereOperands = {whereOperand};

        int affectedRows = db.delete(PlanAccessSchema.TABLE, whereOperands);

        if (affectedRows == -1) {
            throw new SQLException("failed to delete plan accesses");
        }

    }




}