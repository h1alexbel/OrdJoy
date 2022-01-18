package com.ordjoy.dto;

import com.ordjoy.entity.UserRole;

public class UserAccountDto {

    private final Long id;
    private final String login;
    private final String email;
    private final Integer discountPercentageLevel;
    private final UserRole role;

    public UserAccountDto(Long id, String login, String email, Integer discountPercentageLevel, UserRole role) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.discountPercentageLevel = discountPercentageLevel;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public Integer getDiscountPercentageLevel() {
        return discountPercentageLevel;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccountDto userDto = (UserAccountDto) o;

        return id != null ? id.equals(userDto.id) : userDto.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserAccountDto{" +
               "id=" + id +
               ", login='" + login + '\'' +
               ", email='" + email + '\'' +
               ", discountPercentageLevel=" + discountPercentageLevel +
               ", role=" + role +
               '}';
    }
}