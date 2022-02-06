package com.ordjoy.mapper;

import com.ordjoy.dto.OrderDto;
import com.ordjoy.entity.Order;

public class OrderMapper implements Mapper<Order, OrderDto> {

    private static final OrderMapper INSTANCE = new OrderMapper();

    private OrderMapper() {

    }

    public static OrderMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public OrderDto mapFrom(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .price(order.getPrice())
                .userAccount(UserAccountMapper.getInstance().mapFrom(order.getUserAccount()))
                .track(TrackMapper.getInstance().mapFrom(order.getTrack()))
                .status(order.getOrderStatus())
                .build();
    }
}