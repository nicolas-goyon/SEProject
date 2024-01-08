package com.SEApp.app.model.classes;

import com.SEApp.app.model.persist.utils.PasswordEncrypt;
import com.SEApp.app.model.persist.utils.UpdateOperand;
import com.SEApp.app.model.persist.schemas.MemberSchema;

/**
 * 
 */
public class Member {


    private int id;

    /**
     * username of the user
     */
    private String username;

    /**
     * email of the user
     */
    private String email;

    /**
     * password of the user
     */
    private String password;


    /**
     * role of the user
     */
    private String role;

    /**
     * plan_id that the user is subscribed to
     */
    private Integer plan_id;

    private Integer paymentType_id;

    /* ajouter un bouton qui permet de dire si c'est payé ou pas */
    private boolean payment;


    /**
     * Default constructor
     */
    public Member(int id, String username, String email, String password, boolean isPasswordEncrypted) {
        this.id = id;
        this.username = username;
        this.email = email;
        setPassword(password, isPasswordEncrypted);
        this.role = role;
        this.plan_id = null;
        this.paymentType_id = null;
    }

    public Member(String username, String email, String password, boolean isPasswordEncrypted) {
        this.id = -1;
        this.username = username;
        this.email = email;
        setPassword(password, isPasswordEncrypted);
        this.role = role;
        this.plan_id = null;
    }


    public Member(int id,String username, String email, String password, String role, Integer plan_id, Integer paymentType_id, boolean isPasswordEncrypted) {
        this.id = id;
        this.username = username;
        this.email = email;
        setPassword(password, isPasswordEncrypted);
        this.role = role;
        this.plan_id = plan_id;
        this.paymentType_id = paymentType_id;
    }

    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    public void setPassword(String password, boolean isAlreadyEncrypted) {
        if (isAlreadyEncrypted) {
            this.password = password;
        } else {
            this.password = PasswordEncrypt.encrypt(password);
        }
    }

    public boolean checkPassword(String password, boolean isAlreadyEncrypted) {
        if (isAlreadyEncrypted) {
            return this.password.equals(password);
        } else {
            return this.password.equals(PasswordEncrypt.encrypt(password));
        }
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String toString() {
        return "User{" +
                MemberSchema.ID + "=" + id +
                ", " + MemberSchema.USERNAME +"='" + username + '\'' +
                ", " + MemberSchema.EMAIL +"='" + email + '\'' +
                ", " + MemberSchema.PASSWORD +"=" + password +
                ", " + MemberSchema.ROLE +"='" + role + '\'' +
                ", " + MemberSchema.PLAN_ID +"=" + plan_id +
                ", " + MemberSchema.PAYMENT_TYPE_ID +"=" + paymentType_id +
                '}';
    }

    @SuppressWarnings("rawtypes")
    public UpdateOperand[] toUpdateOperands() {
        @SuppressWarnings("redundant")
        UpdateOperand[] values = {
                new UpdateOperand<>(MemberSchema.USERNAME, this.username),
                new UpdateOperand<>(MemberSchema.EMAIL, this.email),
                new UpdateOperand<>(MemberSchema.PASSWORD, this.password),
                new UpdateOperand<>(MemberSchema.ROLE, this.role),
                new UpdateOperand<>(MemberSchema.PLAN_ID, this.plan_id),
                new UpdateOperand<>(MemberSchema.PAYMENT_TYPE_ID, this.paymentType_id)
        };
        return values;
    }

    public int getId() {
        return id;
    }

    public void setId(int insertedID) {
        this.id = insertedID;
    }

    public Integer getPlan() {
        return plan_id;
    }

    public void setPlan(Integer plan_id) {
        this.plan_id = plan_id;
    }

    public Integer getPaymentType() {
        return paymentType_id;
    }

    public void setPaymentType(Integer paymentType_id) {
        this.paymentType_id = paymentType_id;
    }
}