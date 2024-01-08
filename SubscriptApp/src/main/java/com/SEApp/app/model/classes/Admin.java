package com.SEApp.app.model.classes;

import com.SEApp.app.model.persist.utils.PasswordEncrypt;

public class Admin implements User {

    public Role getRole() {
        return Role.ADMIN;
    }

    private String username;
    private String password;

    public Admin(String username, String password, boolean isEncrypted) {
        this.username = username;
        if (isEncrypted) {
            this.password = password;
        } else {
            this.password = PasswordEncrypt.encrypt(password);
        }
    }

    public Admin(String username, String password) {
        this(username, password, false);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean checkPassword(String password, boolean isEncrypted) {
        if (isEncrypted) {
            return this.password.equals(password);
        } else {
            return this.password.equals(PasswordEncrypt.encrypt(password));
        }
    }
}
