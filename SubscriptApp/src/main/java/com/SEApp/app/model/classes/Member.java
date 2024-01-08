package com.SEApp.app.model.classes;

import com.SEApp.app.model.persist.schemas.MemberSchema;
import com.SEApp.app.model.persist.utils.PasswordEncrypt;
import com.SEApp.app.model.persist.utils.UpdateOperand;

/**
 *
 */
public class Member extends User implements Savable  {


    /**
     * id of the Member
     */
    private int id;

    /**
     * username of the Member
     */
    private String username;

    /**
     *  email of the Member
     */
    private String email;

    /**
     *  password of the Member
     */
    private String password;

    /**
     * Default constructor
     * @param id id of the Member
     * @param username username of the Member
     * @param email email of the Member
     * @param password password of the Member
     * @param isAlreadyEncrypted boolean to check if the password is already encrypted
     */
    public Member(int id, String username, String email, String password, boolean isAlreadyEncrypted) {
        super(id, username, email, password, isAlreadyEncrypted);
        setPassword( password, isAlreadyEncrypted);
    }

    /**
     * Default constructor
     * @param username username of the Member
     * @param email email of the Member
     * @param password password of the Member
     * @param isAlreadyEncrypted boolean to check if the password is already encrypted
     */
    public Member(String username, String email, String password, boolean isAlreadyEncrypted) {
        super(username, email, password,isAlreadyEncrypted);
        setPassword(password, isAlreadyEncrypted);

    }




    /**
     * @return the id of the Member
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return the username of the Member
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @return the email of the Member
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @return the password of the Member
     */
    public String getPassword() {
        return this.password;
    }


    /**
     * @param id the id of the Member
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param username the username of the Member
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param email the email of the Member
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param password the password of the Member
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
                new UpdateOperand(MemberSchema.USERNAME, this.username),
                new UpdateOperand(MemberSchema.EMAIL, this.email),
                new UpdateOperand(MemberSchema.PASSWORD, this.password)
        };
    }
}