package com.ordjoy.dto;

import com.ordjoy.entity.OrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDto implements Serializable {

    private final Long id;
    private final BigDecimal price;
    private final UserAccountDto userAccount;
    private final TrackDto track;
    private final OrderStatus status;

    public OrderDto(Long id, BigDecimal price, UserAccountDto userAccount, TrackDto track, OrderStatus status) {
        this.id = id;
        this.price = price;
        this.userAccount = userAccount;
        this.track = track;
        this.status = status;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UserAccountDto getUserAccount() {
        return userAccount;
    }

    public TrackDto getTrack() {
        return track;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDto orderDto = (OrderDto) o;

        return id != null ? id.equals(orderDto.id) : orderDto.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
               "price=" + price +
               ", userAccount=" + userAccount +
               ", track=" + track +
               ", status=" + status +
               '}';
    }
}