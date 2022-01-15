package com.ordjoy.dao;

import com.ordjoy.entity.OrderStatus;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.OrderFilter;
import com.ordjoy.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDao extends GenericDao<Long, Order, OrderFilter> {

    void updateOrderStatus(OrderStatus newStatus, Long orderId);

    List<Order> findOrdersByPrice(BigDecimal price);

    List<Order> findOrdersByUserId(Long userAccountId, DefaultFilter filter);

    List<Order> findOrdersByUserEmail(String email, DefaultFilter filter);

    List<Order> findOrdersByUserLogin(String login, DefaultFilter filter);

    List<Order> findOrdersByTrackId(Long trackId, DefaultFilter filter);

    List<Order> findOrdersByTrackName(String trackName, DefaultFilter filter);

    List<Order> findOrdersByOrderStatus(OrderStatus orderStatus, DefaultFilter filter);
}