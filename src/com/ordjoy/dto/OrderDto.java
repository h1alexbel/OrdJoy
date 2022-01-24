package com.ordjoy.dto;

import java.math.BigDecimal;

public class OrderDto {

    private final Long id;
    private final BigDecimal price;
    private final UserAccountDto userAccount;
    private final TrackDto track;

    public OrderDto(Long id, BigDecimal price, UserAccountDto userAccount, TrackDto track) {
        this.id = id;
        this.price = price;
        this.userAccount = userAccount;
        this.track = track;
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
               " price=" + price +
               ", userAccount=" + userAccount +
               ", track=" + track +
               '}';
    }
}