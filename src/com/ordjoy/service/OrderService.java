package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.OrderFilter;
import com.ordjoy.dao.impl.OrderDaoImpl;
import com.ordjoy.dto.OrderDto;
import com.ordjoy.entity.Order;
import com.ordjoy.entity.OrderStatus;
import com.ordjoy.entity.Track;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.exception.DaoException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.mapper.OrderMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.toList;

public class OrderService {

    private final OrderDaoImpl orderDao = OrderDaoImpl.getInstance();
    private static final OrderService INSTANCE = new OrderService();
    private final OrderMapper orderMapper = OrderMapper.getInstance();
    private static final OrderStatus DEFAULT_STATUS_AFTER_ORDER_MAKE = OrderStatus.ACCEPTED;

    private OrderService() {

    }

    public static OrderService getInstance() {
        return INSTANCE;
    }

    public OrderDto makeOrder(Order order) throws ServiceException {
        try {
            Order savedOrder = orderDao.save(order);
            return orderMapper.mapFrom(savedOrder);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public BigDecimal getCheckForOrder(Long orderId) throws ServiceException {
        BigDecimal result = null;
        Optional<Order> maybeOrder;
        try {
            maybeOrder = orderDao.findById(orderId);
            if (maybeOrder.isPresent()) {
                result = maybeOrder.get().getPrice();
                Integer price = result.intValue();
                Integer discountPercentageLevel = maybeOrder.get().getUserAccount().getDiscountPercentageLevel();
                if (discountPercentageLevel > 0) {
                    result = new BigDecimal(price * discountPercentageLevel);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    public Optional<OrderDto> findOrderById(Long id) throws ServiceException {
        try {
            Optional<Order> maybeOrder = orderDao.findById(id);
            if (maybeOrder.isPresent()) {
                Order order = maybeOrder.get();
                OrderDto orderDto = orderMapper.mapFrom(order);
                return Optional.of(orderDto);
            }
            return Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<OrderDto> findAllOrdersWithLimitAndOffset(OrderFilter filter) throws ServiceException {
        try {
            return orderDao.findAll(filter).stream()
                    .map(orderMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public void updateOrder(Order order) throws ServiceException {
        try {
            orderDao.update(order);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public boolean deleteOrderById(Long id) throws ServiceException {
        try {
            return orderDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public void updateOrderStatus(OrderStatus newStatus, Long orderId) throws ServiceException {
        try {
            orderDao.updateOrderStatus(newStatus, orderId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<OrderDto> findOrdersByPrice(BigDecimal price) throws ServiceException {
        try {
            return orderDao.findOrdersByPrice(price).stream()
                    .map(orderMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<OrderDto> findOrdersByUserId(Long userAccountId, DefaultFilter filter) throws ServiceException {
        try {
            return orderDao.findOrdersByUserId(userAccountId, filter).stream()
                    .map(orderMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<OrderDto> findOrdersByUserEmail(String email, DefaultFilter filter) throws ServiceException {
        try {
            return orderDao.findOrdersByUserEmail(email, filter).stream()
                    .map(orderMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<OrderDto> findOrdersByUserLogin(String login, DefaultFilter filter) throws ServiceException {
        try {
            return orderDao.findOrdersByUserLogin(login, filter).stream()
                    .map(orderMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<OrderDto> findOrdersByTrackId(Long trackId, DefaultFilter filter) throws ServiceException {
        try {
            return orderDao.findOrdersByTrackId(trackId, filter).stream()
                    .map(orderMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<OrderDto> findOrdersByTrackName(String trackName, DefaultFilter filter) throws ServiceException {
        try {
            return orderDao.findOrdersByTrackName(trackName, filter).stream()
                    .map(orderMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<OrderDto> findOrdersByOrderStatus(OrderStatus orderStatus, DefaultFilter filter) throws ServiceException {
        try {
            return orderDao.findOrdersByOrderStatus(orderStatus, filter).stream()
                    .map(orderMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public Order buildOrder(BigDecimal price, UserAccount userAccount, Track track) {
        return new Order(
                price, userAccount, DEFAULT_STATUS_AFTER_ORDER_MAKE, track
        );
    }
}