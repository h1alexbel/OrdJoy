package com.ordjoy.entity;

public enum OrderStatus {

    IN_PROGRESS("In Progress"),
    ACCEPTED("Accepted"),
    CANCELED("Canceled");

    private final String statusName;

    OrderStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}