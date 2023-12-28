package com.SEApp.app.model.persist.Dao.plans;

import com.SEApp.app.model.classes.Plan;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.DBAccess.PostGres;
import com.SEApp.app.model.persist.schemas.PlanSchema;
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
        return new Plan(
                (int) row.get(PlanSchema.ID),
                (String) row.get(PlanSchema.NAME),
                (String) row.get(PlanSchema.DESCRIPTION),
                price,
                null
        );
    }

    @Override
    public Plan save(Plan plan) throws SQLException {
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

        int affectedRows = -1;
        affectedRows = db.update(PlanSchema.TABLE, obj.toUpdateOperands(), whereOperands);

        if (affectedRows != 1) {
            throw new SQLException("failed to update plan (affected rows: " + affectedRows + ")");
        }

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

            plans.add(new Plan(
                    (int) row.get(PlanSchema.ID),
                    (String) row.get(PlanSchema.NAME),
                    (String) row.get(PlanSchema.DESCRIPTION),
                    price,
                    null
            ));
        }

        return plans;
    }
}