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
import com.ordjoy.util.LogginUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.toList;

public class OrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);
    private static final int STANDART_DISCOUNT_PERCENTAGE_LEVEL = 0;
    private static final int PERCENTAGE_AMOUNT = 100;
    private final OrderDaoImpl orderDao = OrderDaoImpl.getInstance();
    private static final OrderService INSTANCE = new OrderService();
    private final OrderMapper orderMapper = OrderMapper.getInstance();
    private static final OrderStatus DEFAULT_STATUS_AFTER_ORDER_MAKE = OrderStatus.ACCEPTED;

    private OrderService() {

    }

    /**
     * @return instance of {@link OrderService}
     */
    public static OrderService getInstance() {
        return INSTANCE;
    }

    /**
     * Save {@link Order} in database
     *
     * @param order entity that be saved in database
     * @return {@link OrderDto} that represents {@link Order} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public OrderDto makeOrder(Order order) throws ServiceException {
        try {
            Order savedOrder = orderDao.save(order);
            return orderMapper.mapFrom(savedOrder);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Update Order's price in database
     *
     * @param price new value of {@link Order} price
     * @param id    {@link Order} id in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public void updateOrderPrice(BigDecimal price, Long id) throws ServiceException {
        if (price != null && id != null) {
            try {
                orderDao.updateOrderPrice(price, id);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
    }

    /**
     * Calculates order price by order id
     *
     * @param orderId {@link Order} id in database
     * @return final price of {@link Order}
     * @throws ServiceException if Dao layer can not execute method
     */
    public BigDecimal calculateOrderPrice(Long orderId) throws ServiceException {
        BigDecimal price = null;
        if (orderId != null) {
            try {
                Optional<Order> maybeOrder = orderDao.findById(orderId);
                if (maybeOrder.isPresent()) {
                    Order order = maybeOrder.get();
                    price = order.getPrice();
                    int value = price.intValue();
                    Integer discountPercentageLevel = order.getUserAccount().getDiscountPercentageLevel();
                    if (discountPercentageLevel > STANDART_DISCOUNT_PERCENTAGE_LEVEL) {
                        int discount = (value * discountPercentageLevel / PERCENTAGE_AMOUNT);
                        price = new BigDecimal(value - discount);
                    }
                }
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return price;
    }

    /**
     * Find {@link Order} in database by id
     *
     * @param id {@link Order} id in database
     * @return {@link Optional} of {@link OrderDto} that represents
     * {@link Order} in database if presents or {@link Optional} empty
     * @throws ServiceException if Dao layer can not execute method
     */
    public Optional<OrderDto> findOrderById(Long id) throws ServiceException {
        if (id != null) {
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
        return Optional.empty();
    }

    /**
     * Finds all {@link OrderDto} that represents {@link Order} in database
     *
     * @param filter sets limit, offset and optional values (price ,status)
     * @return List of {@link OrderDto} from database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<OrderDto> findAllOrdersWithLimitAndOffset(OrderFilter filter) throws ServiceException {
        if (filter != null) {
            try {
                return orderDao.findAll(filter).stream()
                        .map(orderMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Update {@link Order} in database
     *
     * @param order new value of {@link Order}
     * @throws ServiceException if Dao layer can not execute method
     */
    public void updateOrder(Order order) throws ServiceException {
        if (order != null) {
            try {
                orderDao.update(order);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
    }

    /**
     * Delete {@link Order} from database
     *
     * @param id {@link Order} id in database
     * @return boolean value {@code true} if successfully deleted or {@code false} if it failed
     * @throws ServiceException if Dao layer can not execute method
     */
    public boolean deleteOrderById(Long id) throws ServiceException {
        boolean result = false;
        if (id != null) {
            try {
                result = orderDao.deleteById(id);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return result;
    }

    /**
     * Update Order's status by {@link Order} id
     *
     * @param newStatus new status to set
     * @param orderId   {@link Order} id in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public void updateOrderStatus(OrderStatus newStatus, Long orderId) throws ServiceException {
        if (newStatus != null && orderId != null) {
            try {
                orderDao.updateOrderStatus(newStatus, orderId);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
    }

    /**
     * Finds all {@link OrderDto} by {@link Order} price
     *
     * @param price {@link Order} price in database
     * @return List of {@link OrderDto} that represents {@link Order} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<OrderDto> findOrdersByPrice(BigDecimal price) throws ServiceException {
        if (price != null) {
            try {
                return orderDao.findOrdersByPrice(price).stream()
                        .map(orderMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Finds all {@link OrderDto} by {@link UserAccount} id
     *
     * @param userAccountId {@link UserAccount} id in database
     * @param filter        sets limit/offset to query
     * @return List of {@link OrderDto} that represents {@link Order} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<OrderDto> findOrdersByUserId(Long userAccountId, DefaultFilter filter) throws ServiceException {
        if (userAccountId != null && filter != null) {
            try {
                return orderDao.findOrdersByUserId(userAccountId, filter).stream()
                        .map(orderMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Gets all recorded fields from relevant table in database
     *
     * @return Long value that represents all records in table
     * @throws ServiceException if Dao layer can not execute method
     */
    public Long getRecords() throws ServiceException {
        try {
            return orderDao.getTableRecords();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, LogginUtils.FETCH_RECORDS_ERROR, e);
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Finds all {@link OrderDto} by {@link UserAccount} email
     *
     * @param email  {@link UserAccount} email in database
     * @param filter sets limit/offset to query
     * @return List of {@link OrderDto} that represents {@link Order} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<OrderDto> findOrdersByUserEmail(String email, DefaultFilter filter) throws ServiceException {
        if (email != null && filter != null) {
            try {
                return orderDao.findOrdersByUserEmail(email, filter).stream()
                        .map(orderMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Finds all {@link OrderDto}by {@link UserAccount} login
     *
     * @param login  {@link UserAccount} login in database
     * @param filter sets limit/offset to query
     * @return List of {@link OrderDto} that represents {@link Order} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<OrderDto> findOrdersByUserLogin(String login, DefaultFilter filter) throws ServiceException {
        if (login != null && filter != null) {
            try {
                return orderDao.findOrdersByUserLogin(login, filter).stream()
                        .map(orderMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Finds all {@link OrderDto} by {@link Track} id
     *
     * @param trackId {@link Track} id in database
     * @param filter  sets limit/offset to query
     * @return List of {@link OrderDto} that represents {@link Order} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<OrderDto> findOrdersByTrackId(Long trackId, DefaultFilter filter) throws ServiceException {
        if (trackId != null && filter != null) {
            try {
                return orderDao.findOrdersByTrackId(trackId, filter).stream()
                        .map(orderMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Finds all {@link OrderDto} by {@link Track} title
     *
     * @param trackName {@link Track} trackName in database
     * @param filter    sets limit/offset to query
     * @return List of {@link OrderDto} that represents {@link Order} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<OrderDto> findOrdersByTrackName(String trackName, DefaultFilter filter)
            throws ServiceException {
        if (trackName != null && filter != null) {
            try {
                return orderDao.findOrdersByTrackName(trackName, filter).stream()
                        .map(orderMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Finds all {@link OrderDto} in database by {@link Order} status
     *
     * @param orderStatus {@link Order} status in database
     * @param filter      sets limit/offset to query
     * @return List of {@link OrderDto} that represents {@link Order} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<OrderDto> findOrdersByOrderStatus(OrderStatus orderStatus, DefaultFilter filter)
            throws ServiceException {
        if (orderStatus != null && filter != null) {
            try {
                return orderDao.findOrdersByOrderStatus(orderStatus, filter).stream()
                        .map(orderMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Make {@link Order} from data
     *
     * @param price       Order's price
     * @param userAccount Order's userAccount
     * @param track       Order's track
     * @return {@link Order} entity from data
     */
    public Order buildOrder(BigDecimal price, UserAccount userAccount, Track track) {
        return Order.builder()
                .price(price)
                .userAccount(userAccount)
                .orderStatus(DEFAULT_STATUS_AFTER_ORDER_MAKE)
                .track(track)
                .build();
    }
}