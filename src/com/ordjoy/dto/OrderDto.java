package com.ordjoy.dto;

import java.math.BigDecimal;

public class OrderDto {

    private final Long id;
    private final BigDecimal price;
    private final UserDto user;
    private final TrackDto track;

    public OrderDto(Long id, BigDecimal price, UserDto userDto, TrackDto trackDto) {
        this.id = id;
        this.price = price;
        this.user = userDto;
        this.track = trackDto;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UserDto getUser() {
        return user;
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
               "id=" + id +
               ", price=" + price +
               ", user=" + user +
               ", track=" + track +
               '}';
    }
}