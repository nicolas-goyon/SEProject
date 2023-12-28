package com.SEApp.app.model.classes;

import com.SEApp.app.model.persist.schemas.ManagerSchema;
import com.SEApp.app.model.persist.utils.PasswordEncrypt;
import com.SEApp.app.model.persist.utils.UpdateOperand;

/**
 * 
 */
public class Manager implements Savable {


    /**
     * id of the manager
     */
    private long id;

    /**
     * username of the manager
     */
    private String username;

    /**
     *  email of the manager
     */
    private String email;

    /**
     *  password of the manager
     */
    private String password;

    /**
     * Default constructor
     * @param id id of the manager
     * @param username username of the manager
     * @param email email of the manager
     * @param password password of the manager
     * @param isAlreadyEncrypted boolean to check if the password is already encrypted
     */
    public Manager(long id, String username, String email, String password, boolean isAlreadyEncrypted) {
        this.id = id;
        this.username = username;
        this.email = email;
        setPassword(password, isAlreadyEncrypted);

    }

    /**
     * @return the id of the manager
     */
    public long getId() {
        return this.id;
    }

    /**
     * @return the username of the manager
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @return the email of the manager
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @return the password of the manager
     */
    public String getPassword() {
        return this.password;
    }


    /**
     * @param id the id of the manager
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @param username the username of the manager
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param email the email of the manager
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param password the password of the manager
     * @param isAlreadyEncrypted boolean to check if the password is already encrypted
     */
    public void setPassword(String password, boolean isAlreadyEncrypted) {
        if (isAlreadyEncrypted) {
            this.password = password;
        } else {
            this.password = PasswordEncrypt.encrypt(password);
        }
    }

    /**
     * @return
     */
    @Override
    public UpdateOperand[] toUpdateOperands() {
        return new UpdateOperand[]{
                new UpdateOperand(ManagerSchema.USERNAME, this.username),
                new UpdateOperand(ManagerSchema.EMAIL, this.email),
                new UpdateOperand(ManagerSchema.PASSWORD, this.password)
        };
    }
}