package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.OrderFilter;
import com.ordjoy.dao.impl.OrderDaoImpl;
import com.ordjoy.dto.OrderDto;
import com.ordjoy.entity.Order;
import com.ordjoy.entity.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class OrderService {

    private final OrderDaoImpl orderDao = OrderDaoImpl.getInstance();

    private static final OrderService INSTANCE = new OrderService();


    private OrderService() {

    }

    public static OrderService getInstance() {
        return INSTANCE;
    }

    public Order saveOrder(Order order) {
        return orderDao.save(order);
    }

    public Optional<OrderDto> findOrderById(Long id) {
        return orderDao.findById(id).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).findFirst();
    }

    public List<OrderDto> findAllOrders(OrderFilter filter) {
        return orderDao.findAll(filter).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).collect(toList());
    }

    public void update(Order order) {
        orderDao.update(order);
    }

    public boolean deleteById(Long id) {
        return orderDao.deleteById(id);
    }

    public void updateOrderStatus(OrderStatus newStatus, Long orderId) {
        orderDao.updateOrderStatus(newStatus, orderId);
    }

    public List<OrderDto> findOrdersByPrice(BigDecimal price) {
        return orderDao.findOrdersByPrice(price).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).collect(toList());
    }

    public List<OrderDto> findOrdersByUserId(Long userAccountId, DefaultFilter filter) {
        return orderDao.findOrdersByUserId(userAccountId, filter).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).collect(toList());
    }

    public List<OrderDto> findOrdersByUserEmail(String email, DefaultFilter filter) {
        return orderDao.findOrdersByUserEmail(email, filter).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).collect(toList());
    }

    public List<OrderDto> findOrdersByUserLogin(String login, DefaultFilter filter) {
        return orderDao.findOrdersByUserLogin(login, filter).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).collect(toList());
    }

    public List<OrderDto> findOrdersByTrackId(Long trackId, DefaultFilter filter) {
        return orderDao.findOrdersByTrackId(trackId, filter).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).collect(toList());
    }

    public List<OrderDto> findOrdersByTrackName(String trackName, DefaultFilter filter) {
        return orderDao.findOrdersByTrackName(trackName, filter).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).collect(toList());
    }

    public List<OrderDto> findOrdersByOrderStatus(OrderStatus orderStatus, DefaultFilter filter) {
        return orderDao.findOrdersByOrderStatus(orderStatus, filter).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).collect(toList());
    }
}