package com.ordjoy.entity;

public class UserAccountData implements Entity {

    private String firstName;
    private String lastName;
    private int age;
    private UserAccount userAccount;

    public UserAccountData(String firstName, String lastName, int age, UserAccount userAccount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.userAccount = userAccount;
    }

    public UserAccountData(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public UserAccountData() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccountData that = (UserAccountData) o;

        return firstName != null ? firstName.equals(that.firstName) : that.firstName == null;
    }

    @Override
    public int hashCode() {
        return firstName != null ? firstName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserAccountData{" +
               "firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", age=" + age +
               ", userAccount=" + userAccount +
               '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}