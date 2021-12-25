package com.ordjoy.entity;

public enum UserRole {

    CLIENT_ROLE("Client"),
    ADMIN_ROLE("Admin");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}