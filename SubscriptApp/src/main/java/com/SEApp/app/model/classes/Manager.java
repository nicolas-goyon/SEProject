package com.SEApp.app.model.classes;

import com.SEApp.app.model.persist.schemas.ManagerSchema;
import com.SEApp.app.model.persist.utils.PasswordEncrypt;
import com.SEApp.app.model.persist.utils.UpdateOperand;

/**
 * 
 */
public class Manager implements Savable, User {

    public Role getRole() {
        return Role.MANAGER;
    }


    /**
     * id of the manager
     */
    private int id;

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
    public Manager(int id, String username, String email, String password, boolean isAlreadyEncrypted) {
        this.id = id;
        this.username = username;
        this.email = email;
        setPassword(password, isAlreadyEncrypted);

    }

    /**
     * Default constructor
     * @param username username of the manager
     * @param email email of the manager
     * @param password password of the manager
     * @param isAlreadyEncrypted boolean to check if the password is already encrypted
     */
    public Manager(String username, String email, String password, boolean isAlreadyEncrypted) {
        this.id = -1;
        this.username = username;
        this.email = email;
        setPassword(password, isAlreadyEncrypted);

    }

    /**
     * @return the id of the manager
     */
    public int getId() {
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
    public void setId(int id) {
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

    public boolean checkPassword(String password, boolean isEncrypted) {
        if (isEncrypted) {
            return this.password.equals(password);
        } else {
            return this.password.equals(PasswordEncrypt.encrypt(password));
        }
    }
}