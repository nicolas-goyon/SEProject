package com.SEApp.app.model.classes;

/**
 * 
 */
public class User {


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
    public String password;

    /**
     *
     */
    private String role;


    /**
     * Default constructor
     */
    public User( String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password=" + password +
                ", role='" + role + '\'' +
                '}';
    }

}