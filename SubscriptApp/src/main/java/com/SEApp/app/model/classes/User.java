package com.SEApp.app.model.classes;

import com.SEApp.app.model.persist.PasswordEncrypt;
import com.SEApp.app.model.persist.UpdateOperand;
import com.SEApp.app.model.persist.schemas.UserSchema;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class User {


    private long id;

    /**
     *
     */
    private String username;

    /**
     *
     */
    private String email;

    /**
     *
     */
    private String password;

    private boolean isPasswordEncrypted;

    /**
     *
     */
    private String role;


    /**
     * Default constructor
     */
    public User(long id, String username, String email, String password, String role, boolean isPasswordEncrypted) {
        this.id = id;
        this.username = username;
        this.email = email;
        setPassword(password, isPasswordEncrypted);
        this.role = role;
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

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(UserSchema.USERNAME, this.username);
        map.put(UserSchema.EMAIL, this.email);
        map.put(UserSchema.PASSWORD, this.password);
        map.put(UserSchema.ROLE, this.role);
        return map;
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

    public long getId() {
        return id;
    }

    public void setId(int insertedID) {
        this.id = insertedID;
    }
}