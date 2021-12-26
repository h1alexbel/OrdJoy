package com.ordjoy.entity;

import java.math.BigDecimal;
import java.util.List;

public class Order implements Entity {

    private Long id;
    private BigDecimal price;
    private Long cardNumber;
    private UserAccount userAccount;
    private List<Track> tracks;

    public Order(BigDecimal price, Long cardNumber, UserAccount userAccount, List<Track> tracks) {
        this.price = price;
        this.cardNumber = cardNumber;
        this.userAccount = userAccount;
        this.tracks = tracks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (price != null ? !price.equals(order.price) : order.price != null) return false;
        if (cardNumber != null ? !cardNumber.equals(order.cardNumber) : order.cardNumber != null) return false;
        if (userAccount != null ? !userAccount.equals(order.userAccount) : order.userAccount != null) return false;
        return tracks != null ? tracks.equals(order.tracks) : order.tracks == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (userAccount != null ? userAccount.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        return result;
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

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
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