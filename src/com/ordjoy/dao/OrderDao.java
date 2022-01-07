package com.ordjoy.dao;

import com.ordjoy.entity.Track;
import com.ordjoy.filter.DefaultFilter;
import com.ordjoy.filter.OrderFilter;
import com.ordjoy.entity.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Order saveOrder(Order order, Track orderedTrack);

    Optional<Order> findById(Long id);

    Optional<List<Order>> findOrdersByPrice(BigDecimal price, DefaultFilter filter);

    Optional<List<Order>> findOrdersByTrackId(Long trackId, DefaultFilter filter);

    Optional<List<Order>> findOrdersByCardNumber(String cardNumber, DefaultFilter filter);

    Optional<List<Order>> findOrdersByUserAccountId(Long userId, DefaultFilter filter);

    Optional<List<Order>> findOrdersByUserAccountName(String userName, DefaultFilter filter);

    List<Order> findAll(OrderFilter filter);

    boolean deleteById(Long id);

    void update(Order order);
}
