package com.ordjoy.entity;

import java.util.Set;

public class UserAccount implements Entity {

    private Long id;
    private String email;
    private String login;
    private String password;
    private Integer discountPercentageLevel;
    private UserData userData;
    private Set<Review> reviews;

    public UserAccount() {
    }

    public UserAccount(Long id, String email, String login, String password, Integer discountPercentageLevel, UserData userData, Set<Review> reviews) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.password = password;
        this.discountPercentageLevel = discountPercentageLevel;
        this.userData = userData;
        this.reviews = reviews;
    }

    public UserAccount(String email, String login, String password, Integer discountPercentageLevel, UserData userData, Set<Review> reviews) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.discountPercentageLevel = discountPercentageLevel;
        this.userData = userData;
        this.reviews = reviews;
    }

    public UserAccount(String email, String login, String password, Integer discountPercentageLevel, UserData userData) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.discountPercentageLevel = discountPercentageLevel;
        this.userData = userData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDiscountPercentageLevel() {
        return discountPercentageLevel;
    }

    public void setDiscountPercentageLevel(Integer discountPercentageLevel) {
        this.discountPercentageLevel = discountPercentageLevel;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccount account = (UserAccount) o;

        return id != null ? id.equals(account.id) : account.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
               "id=" + id +
               ", email='" + email + '\'' +
               ", login='" + login + '\'' +
               ", password='" + password + '\'' +
               ", discountPercentageLevel=" + discountPercentageLevel +
               ", userData=" + userData +
               ", reviews=" + reviews +
               '}';
    }
}