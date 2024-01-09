package com.SEApp.app.model.persist.Dao.plans;

import com.SEApp.app.model.classes.Access;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.DBAccess.PostGres;
import com.SEApp.app.model.persist.schemas.AccessSchema;
import com.SEApp.app.model.persist.schemas.PlanAccessSchema;
import com.SEApp.app.model.persist.utils.WhereOperand;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class AccessDaoPostGres extends AccessDao {

    /**
     * Default constructor
     *
     * @param db
     */
    public AccessDaoPostGres(PostGres db) {
        super(db);
    }


    /**
     * @param id of user to get
     * @return the user
     */
    public Access get(int id) throws SQLException {
        String[] columns = {AccessSchema.ID, AccessSchema.NAME, AccessSchema.DESCRIPTION};

        Map<String, Object> row = db.getByKey(AccessSchema.TABLE, columns, AccessSchema.ID, id);

        if (row == null) {
            return null;
        }

        return new Access(
                (int) row.get(AccessSchema.ID),
                (String) row.get(AccessSchema.NAME),
                (String) row.get(AccessSchema.DESCRIPTION)
        );
    }

    @Override
    public Access create(Access access) throws SQLException {
        if (access == null) {
            throw new IllegalArgumentException("access cannot be null");
        }
        if (access.getId() != -1) {
            throw new IllegalArgumentException("access id must be -1, use update instead");
        }

        int insertedID = -1;
        insertedID = db.create(AccessSchema.TABLE, access.toUpdateOperands());
        if (insertedID == -1) {
            throw new SQLException("failed to insert access");
        }

        access.setId(insertedID);

        return access;
    }

    @Override
    public Access update(Access obj) throws SQLException, IncorrectOperandException {
        if (obj == null) {
            throw new IllegalArgumentException("access cannot be null");
        }

        if (obj.getId() == -1) {
            throw new IllegalArgumentException("access id cannot be -1, use save instead");
        }

        WhereOperand<Integer> whereOperand = new WhereOperand<Integer>(AccessSchema.ID, "=", obj.getId());
        @SuppressWarnings("rawtypes")
        WhereOperand[] whereOperands = {whereOperand};

        int affectedRows = -1;
        affectedRows = db.update(AccessSchema.TABLE, obj.toUpdateOperands(), whereOperands);

        if (affectedRows != 1) {
            throw new SQLException("failed to update access (affected rows: " + affectedRows + ")");
        }

        return obj;
    }

    @Override
    public boolean delete(Access access) throws SQLException, IncorrectOperandException {
        if (access == null) {
            throw new IllegalArgumentException("access cannot be null");
        }
        if (access.getId() == -1) {
            throw new IllegalArgumentException("access id cannot be -1");
        }

        deletePlanAccess(access.getId());

        WhereOperand<Integer> whereOperand = new WhereOperand<Integer>(AccessSchema.ID, "=", access.getId());
        @SuppressWarnings("rawtypes")
        WhereOperand[] whereOperands = {whereOperand};

        int affectedRows = db.delete(AccessSchema.TABLE, whereOperands);
        if (affectedRows != 1) {
            throw new RuntimeException("failed to delete access (affected rows: " + affectedRows + ")");
        }


        return true;

    }

    @Override
    public List<Access> list() throws SQLException {
        String[] columns = {AccessSchema.ID, AccessSchema.NAME, AccessSchema.DESCRIPTION};
        Map<String, Object>[] res = db.read(AccessSchema.TABLE, columns, null);

        List<Access> accesses = new ArrayList<>();


        for (Map<String, Object> row : res) {

            accesses.add(new Access(
                    (int) row.get(AccessSchema.ID),
                    (String) row.get(AccessSchema.NAME),
                    (String) row.get(AccessSchema.DESCRIPTION)
            ));
        }

        return accesses;
    }

    private void deletePlanAccess(int id) throws SQLException, IncorrectOperandException {
        WhereOperand<Integer> whereOperand = new WhereOperand<Integer>(PlanAccessSchema.ACCESS_ID, "=", id);
        @SuppressWarnings("rawtypes")
        WhereOperand[] whereOperands = {whereOperand};

        db.delete(PlanAccessSchema.TABLE, whereOperands);
    }

}