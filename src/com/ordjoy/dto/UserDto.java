package com.ordjoy.dto;

public class UserDto {

    private final Long id;
    private final String login;
    private final String email;
    private final Integer discountPercentageLevel;

    public UserDto(Long id, String login, String email, Integer discountPercentageLevel) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.discountPercentageLevel = discountPercentageLevel;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        return id != null ? id.equals(userDto.id) : userDto.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserDto{" +
               "id=" + id +
               ", login='" + login + '\'' +
               ", email='" + email + '\'' +
               ", discountPercentageLevel=" + discountPercentageLevel +
               '}';
    }
}