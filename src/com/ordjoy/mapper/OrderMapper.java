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
        return new OrderDto(
                order.getId(),
                order.getPrice(),
                UserAccountMapper.getInstance().mapFrom(order.getUserAccount()),
                TrackMapper.getInstance().mapFrom(order.getTrack()),
                order.getOrderStatus()
        );
    }
}