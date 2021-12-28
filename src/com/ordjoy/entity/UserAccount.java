package com.ordjoy.entity;

public class UserAccount implements Entity {

    private Long id;
    private String email;
    private String login;
    private String password;
    private UserRole userRole;

    public UserAccount(String email, String login, String password, UserRole userRole) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.userRole = userRole;
    }

    public UserAccount() {
    }

    public UserAccount(Long id, String email, String login, String password, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.password = password;
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccount that = (UserAccount) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return userRole == that.userRole;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
               "id=" + id +
               ", email='" + email + '\'' +
               ", login='" + login + '\'' +
               ", password='" + password + '\'' +
               ", userRole=" + userRole +
               '}';
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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}