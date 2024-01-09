package com.SEApp.app.model.classes;

public enum Role {
    ADMIN("admin"),
    MANAGER("manager"),
    MEMBER("member");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
