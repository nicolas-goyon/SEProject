package com.SEApp.app.model.persist.Dao.Member;

import com.SEApp.app.model.classes.Member;
import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.DBAccess.PostGres;
import com.SEApp.app.model.persist.schemas.MemberSchema;
import com.SEApp.app.model.persist.utils.WhereOperand;

import java.sql.SQLException;
import java.util.*;

/**
 *
 */
public class MemberDaoPostGres extends MemberDao {


    /**
     * Default constructor
     *
     * @param db to connect to
     */
    public MemberDaoPostGres(PostGres db) {
        super(db);
    }

    /**
     * @param id of user to get
     * @return the user
     */
    public Member get(int id) throws SQLException {
        String[] columns = {MemberSchema.USERNAME, MemberSchema.EMAIL, MemberSchema.PASSWORD, MemberSchema.PAYMENT_TYPE_ID, MemberSchema.PLAN_ID, MemberSchema.LAST_PAYMENT_DATE};

        Map<String, Object> row = db.getByKey(MemberSchema.TABLE, columns, MemberSchema.ID, id);

        if (row == null) {
            return null;
        }

        return memberFromRow(row);
    }

    @Override
    public Member create(Member Member) throws SQLException {
        if (Member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        if (Member.getId() != -1) {
            throw new IllegalArgumentException("Member id must be -1, use update instead");
        }

        int insertedID = -1;
        insertedID = db.create(MemberSchema.TABLE, Member.toUpdateOperands());
        if (insertedID == -1) {
            throw new SQLException("failed to insert Member");
        }

        Member.setId(insertedID);

        return Member;
    }

    @Override
    public Member update(Member obj) throws SQLException, IncorrectOperandException {
        if (obj == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }

        if (obj.getId() == -1) {
            throw new IllegalArgumentException("Member id cannot be -1, use save instead");
        }

        WhereOperand<Integer> whereOperand = new WhereOperand<Integer>(MemberSchema.ID, "=", obj.getId());
        @SuppressWarnings("rawtypes")
        WhereOperand[] whereOperands = {whereOperand};

        int affectedRows = -1;
        affectedRows = db.update(MemberSchema.TABLE, obj.toUpdateOperands(), whereOperands);

        if (affectedRows != 1) {
            throw new SQLException("failed to update Member (affected rows: " + affectedRows + ")");
        }

        return obj;
    }

    @Override
    public boolean delete(Member Member) throws SQLException, IncorrectOperandException {
        if (Member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        if (Member.getId() == -1) {
            throw new IllegalArgumentException("Member id cannot be -1");
        }

        WhereOperand<Integer> whereOperand = new WhereOperand<Integer>(MemberSchema.ID, "=", Member.getId());
        @SuppressWarnings("rawtypes")
        WhereOperand[] whereOperands = {whereOperand};

        int affectedRows = db.delete(MemberSchema.TABLE, whereOperands);
        if (affectedRows != 1) {
            throw new RuntimeException("failed to delete Member (affected rows: " + affectedRows + ")");
        }

        return true;

    }

    @Override
    public List<Member> list() throws SQLException {
        String[] columns = {MemberSchema.ID, MemberSchema.USERNAME, MemberSchema.EMAIL, MemberSchema.PASSWORD, MemberSchema.PLAN_ID, MemberSchema.PAYMENT_TYPE_ID, MemberSchema.LAST_PAYMENT_DATE};
        Map<String, Object>[] res = db.read(MemberSchema.TABLE, columns, null);

        List<Member> Members = new ArrayList<>();
        for (Map<String, Object> row : res) {
            Members.add(memberFromRow(row));
        }

        return Members;
    }

    @Override
    public Member findByEmail(String email) throws SQLException {
        String[] columns = {MemberSchema.ID, MemberSchema.USERNAME, MemberSchema.EMAIL, MemberSchema.PASSWORD, MemberSchema.PLAN_ID, MemberSchema.PAYMENT_TYPE_ID, MemberSchema.LAST_PAYMENT_DATE};

        Map<String, Object> row = null;

        row = db.getByKey(MemberSchema.TABLE, columns, MemberSchema.EMAIL, email);


        if (row == null) {
            return null;
        }

        return memberFromRow(row);
    }

    /**
     * @param username
     * @return
     * @throws SQLException
     */
    @Override
    public Member findByUsername(String username) throws SQLException {
        String[] columns = {MemberSchema.ID, MemberSchema.USERNAME, MemberSchema.EMAIL, MemberSchema.PASSWORD, MemberSchema.PLAN_ID, MemberSchema.PAYMENT_TYPE_ID, MemberSchema.LAST_PAYMENT_DATE};

        Map<String, Object> row = null;

        row = db.getByKey(MemberSchema.TABLE, columns, MemberSchema.USERNAME, username);

        if (row == null) {
            return null;
        }

        return memberFromRow(row);
    }

    private Member memberFromRow(Map<String, Object> row) {
        Member newMember = new Member(
                (int) row.get(MemberSchema.ID),
                (String) row.get(MemberSchema.USERNAME),
                (String) row.get(MemberSchema.EMAIL),
                (String) row.get(MemberSchema.PASSWORD),
                (Integer) row.get(MemberSchema.PLAN_ID),
                (Integer) row.get(MemberSchema.PAYMENT_TYPE_ID),
                true
        );
        newMember.setLastPaymentDate((String) row.get(MemberSchema.LAST_PAYMENT_DATE));
        return newMember;
    }

}