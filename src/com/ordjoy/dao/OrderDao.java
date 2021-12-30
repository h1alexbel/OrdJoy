package com.ordjoy.dao;

import com.ordjoy.dto.OrderFilter;
import com.ordjoy.entity.Order;
import com.ordjoy.entity.UserAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderDao extends GenericDao<Long, Order> {

    Order saveOrder(Order order);

    Optional<List<Order>> findOrdersByPrice(BigDecimal price, OrderFilter filter);

    Optional<List<Order>> findOrdersByTrackId(Long trackId, OrderFilter filter);

    Optional<List<Order>> findOrdersByCardNumber(Long cardNumber, OrderFilter filter);

    Optional<List<Order>> findOrdersByUserAccountId(Long userId, OrderFilter filter);

    List<Order> findAll(OrderFilter filter);

    Order saveOrderWithUserAccount(Order order, UserAccount userAccount);
}