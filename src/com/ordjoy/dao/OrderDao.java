package com.ordjoy.dao;

import com.ordjoy.entity.OrderStatus;
import com.ordjoy.filter.DefaultFilter;
import com.ordjoy.filter.OrderFilter;
import com.ordjoy.entity.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface OrderDao extends GenericDao<Long, Order, OrderFilter> {

    List<Order> findOrdersByPrice(BigDecimal price, DefaultFilter filter);

    Set<Order> findOrdersByUserId(Long userAccountId, DefaultFilter filter);

    Set<Order> findOrdersByUserEmail(String email, DefaultFilter filter);

    Set<Order> findOrdersByUserLogin(String login, DefaultFilter filter);

    List<Order> findOrdersByTrackId(Long trackId, DefaultFilter filter);

    List<Order> findOrdersByTrackName(String trackName, DefaultFilter filter);

    List<Order> findOrdersByOrderStatus(OrderStatus orderStatus, DefaultFilter filter);
}