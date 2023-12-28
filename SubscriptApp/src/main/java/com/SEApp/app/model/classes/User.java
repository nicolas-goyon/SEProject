package com.SEApp.app.model.classes;

import com.SEApp.app.model.persist.utils.PasswordEncrypt;
import com.SEApp.app.model.persist.utils.UpdateOperand;
import com.SEApp.app.model.persist.schemas.UserSchema;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class User {


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
     * plan that the user is subscribed to
     */
    private Plan plan;


    /**
     * Default constructor
     */
    public User(int id, String username, String email, String password, String role, boolean isPasswordEncrypted) {
        this.id = id;
        this.username = username;
        this.email = email;
        setPassword(password, isPasswordEncrypted);
        this.role = role;
        this.plan = null;
    }

    public User(String username, String email, String password, String role, boolean isPasswordEncrypted) {
        this.id = -1;
        this.username = username;
        this.email = email;
        setPassword(password, isPasswordEncrypted);
        this.role = role;
        this.plan = null;
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

    public void setRole(String role) {
        this.role = role;
    }

    public String toString() {
        return "User{" +
                UserSchema.ID + "=" + id +
                ", " + UserSchema.USERNAME +"='" + username + '\'' +
                ", " + UserSchema.EMAIL +"='" + email + '\'' +
                ", " + UserSchema.PASSWORD +"=" + password +
                ", " + UserSchema.ROLE +"='" + role + '\'' +
                '}';
    }

    public UpdateOperand[] toUpdateOperands() {
        UpdateOperand[] values = {
                new UpdateOperand(UserSchema.USERNAME, this.username),
                new UpdateOperand(UserSchema.EMAIL, this.email),
                new UpdateOperand(UserSchema.PASSWORD, this.password),
                new UpdateOperand(UserSchema.ROLE, this.role)
        };
        return values;
    }

    public int getId() {
        return id;
    }

    public void setId(int insertedID) {
        this.id = insertedID;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}