package com.ordjoy.entity;

public class UserData {

    private UserRole userRole;
    private String firstName;
    private String lastName;
    private Integer age;
    private String cardNumber;

    public UserData() {
    }

    public UserData(UserRole userRole, String firstName, String lastName, Integer age, String cardNumber) {
        this.userRole = userRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.cardNumber = cardNumber;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserData userData = (UserData) o;

        if (userRole != userData.userRole) return false;
        if (firstName != null ? !firstName.equals(userData.firstName) : userData.firstName != null) return false;
        if (lastName != null ? !lastName.equals(userData.lastName) : userData.lastName != null) return false;
        if (age != null ? !age.equals(userData.age) : userData.age != null) return false;
        return cardNumber != null ? cardNumber.equals(userData.cardNumber) : userData.cardNumber == null;
    }

    @Override
    public int hashCode() {
        int result = userRole != null ? userRole.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserData{" +
               "userRole=" + userRole +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", age=" + age +
               ", cardNumber='" + cardNumber + '\'' +
               '}';
    }
}
