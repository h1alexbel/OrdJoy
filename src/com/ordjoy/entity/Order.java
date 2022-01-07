package com.ordjoy.entity;

import java.math.BigDecimal;
import java.util.List;

public class Order implements Entity {

    private Long id;
    private BigDecimal price;
    private String cardNumber;
    private UserAccount userAccount;
    private List<Track> tracks;

    public Order(Long id, BigDecimal price, String cardNumber, UserAccount userAccount, List<Track> tracks) {
        this.id = id;
        this.price = price;
        this.cardNumber = cardNumber;
        this.userAccount = userAccount;
        this.tracks = tracks;
    }

    public Order(BigDecimal price, String cardNumber, UserAccount userAccount, List<Track> tracks) {
        this.price = price;
        this.cardNumber = cardNumber;
        this.userAccount = userAccount;
        this.tracks = tracks;
    }

    public Order() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return id != null ? id.equals(order.id) : order.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Order{" +
               "id=" + id +
               ", price=" + price +
               ", cardNumber=" + cardNumber +
               ", userAccount=" + userAccount +
               ", tracks=" + tracks +
               '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}