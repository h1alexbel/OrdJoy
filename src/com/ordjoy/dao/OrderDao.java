package com.ordjoy.dao;

import com.ordjoy.entity.OrderStatus;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.OrderFilter;
import com.ordjoy.entity.Order;
import com.ordjoy.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDao extends GenericDao<Long, Order, OrderFilter> {

    void updateOrderStatus(OrderStatus newStatus, Long orderId) throws DaoException;

    List<Order> findOrdersByPrice(BigDecimal price) throws DaoException;

    List<Order> findOrdersByUserId(Long userAccountId, DefaultFilter filter) throws DaoException;

    List<Order> findOrdersByUserEmail(String email, DefaultFilter filter) throws DaoException;

    List<Order> findOrdersByUserLogin(String login, DefaultFilter filter) throws DaoException;

    List<Order> findOrdersByTrackId(Long trackId, DefaultFilter filter) throws DaoException;

    List<Order> findOrdersByTrackName(String trackName, DefaultFilter filter) throws DaoException;

    List<Order> findOrdersByOrderStatus(OrderStatus orderStatus, DefaultFilter filter) throws DaoException;
}