package com.SEApp.app.model.classes;

/**
 * 
 */
public class User {

    /**
     * Default constructor
     */
    public User( String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * 
     */
    public String username;

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

    // TODO implement accessors and mutators

}