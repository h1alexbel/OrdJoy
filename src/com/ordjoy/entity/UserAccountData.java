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

    public UserAccountData() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccountData that = (UserAccountData) o;

        if (age != that.age) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        return userAccount != null ? userAccount.equals(that.userAccount) : that.userAccount == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (userAccount != null ? userAccount.hashCode() : 0);
        return result;
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