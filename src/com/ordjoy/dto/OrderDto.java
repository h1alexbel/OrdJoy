package com.ordjoy.dto;

import java.math.BigDecimal;

public class OrderDto {

    private final Long id;
    private final BigDecimal price;
    private final String userName;
    private final String trackName;

    public OrderDto(Long id, BigDecimal price, String userName, String trackName) {
        this.id = id;
        this.price = price;
        this.userName = userName;
        this.trackName = trackName;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getUserName() {
        return userName;
    }

    public String getTrackName() {
        return trackName;
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
               "id=" + id +
               ", price=" + price +
               ", userName='" + userName + '\'' +
               ", trackName='" + trackName + '\'' +
               '}';
    }
}