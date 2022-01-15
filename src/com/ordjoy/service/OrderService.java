package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.OrderFilter;
import com.ordjoy.dao.impl.OrderDaoImpl;
import com.ordjoy.dto.OrderDto;
import com.ordjoy.entity.Order;
import com.ordjoy.entity.OrderStatus;
import com.ordjoy.exception.ServiceException;

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

    public Order saveOrder(Order order) throws ServiceException {
        return orderDao.save(order);
    }

    public Optional<OrderDto> findOrderById(Long id) throws ServiceException {
        return orderDao.findById(id).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).findFirst();
    }

    public List<OrderDto> findAllOrders(OrderFilter filter) throws ServiceException {
        return orderDao.findAll(filter).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).collect(toList());
    }

    public void update(Order order) throws ServiceException {
        orderDao.update(order);
    }

    public boolean deleteById(Long id) throws ServiceException {
        return orderDao.deleteById(id);
    }

    public void updateOrderStatus(OrderStatus newStatus, Long orderId) throws ServiceException {
        orderDao.updateOrderStatus(newStatus, orderId);
    }

    public List<OrderDto> findOrdersByPrice(BigDecimal price) throws ServiceException {
        return orderDao.findOrdersByPrice(price).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).collect(toList());
    }

    public List<OrderDto> findOrdersByUserId(Long userAccountId, DefaultFilter filter) throws ServiceException {
        return orderDao.findOrdersByUserId(userAccountId, filter).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).collect(toList());
    }

    public List<OrderDto> findOrdersByUserEmail(String email, DefaultFilter filter) throws ServiceException {
        return orderDao.findOrdersByUserEmail(email, filter).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).collect(toList());
    }

    public List<OrderDto> findOrdersByUserLogin(String login, DefaultFilter filter) throws ServiceException {
        return orderDao.findOrdersByUserLogin(login, filter).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).collect(toList());
    }

    public List<OrderDto> findOrdersByTrackId(Long trackId, DefaultFilter filter) throws ServiceException {
        return orderDao.findOrdersByTrackId(trackId, filter).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).collect(toList());
    }

    public List<OrderDto> findOrdersByTrackName(String trackName, DefaultFilter filter) throws ServiceException {
        return orderDao.findOrdersByTrackName(trackName, filter).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).collect(toList());
    }

    public List<OrderDto> findOrdersByOrderStatus(OrderStatus orderStatus, DefaultFilter filter) throws ServiceException {
        return orderDao.findOrdersByOrderStatus(orderStatus, filter).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getPrice(),
                        order.getUserAccount().getLogin(),
                        order.getTrack().getTitle()
                )).collect(toList());
    }
}