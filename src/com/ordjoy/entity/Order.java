package com.ordjoy.entity;

import java.math.BigDecimal;

public class Order implements Entity {

    private Long id;
    private BigDecimal price;
    private UserAccount userAccount;
    private OrderStatus orderStatus;
    private Track track;

    public Order() {
    }

    public Order(Long id, BigDecimal price, UserAccount userAccount, OrderStatus orderStatus, Track track) {
        this.id = id;
        this.price = price;
        this.userAccount = userAccount;
        this.orderStatus = orderStatus;
        this.track = track;
    }

    public Order(BigDecimal price, UserAccount userAccount, OrderStatus orderStatus, Track track) {
        this.price = price;
        this.userAccount = userAccount;
        this.orderStatus = orderStatus;
        this.track = track;
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

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
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
               ", userAccount=" + userAccount +
               ", orderStatus=" + orderStatus +
               ", track=" + track +
               '}';
    }
}