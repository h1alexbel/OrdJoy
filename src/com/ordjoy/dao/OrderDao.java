package com.ordjoy.dao;

import com.ordjoy.entity.OrderStatus;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.OrderFilter;
import com.ordjoy.entity.Order;
import com.ordjoy.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDao extends GenericDao<Long, Order, OrderFilter> {

    /**
     * Update {@link OrderStatus} in database
     *
     * @param newStatus new value of {@link OrderStatus}
     * @param orderId   {@link Order} id in database
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    void updateOrderStatus(OrderStatus newStatus, Long orderId) throws DaoException;

    /**
     * Update Order's price in database
     *
     * @param price   new value of {@link Order} price
     * @param orderId {@link Order} id in database
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    void updateOrderPrice(BigDecimal price, Long orderId) throws DaoException;

    /**
     * Finds List of {@link Order} by it's price from database
     *
     * @param price {@link Order} price in database
     * @return List of {@link Order}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<Order> findOrdersByPrice(BigDecimal price) throws DaoException;

    /**
     * Finds List of {@link Order} by {@link com.ordjoy.entity.UserAccount} id from database
     *
     * @param userAccountId {@link com.ordjoy.entity.UserAccount} id in database
     * @return List of {@link Order}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<Order> findOrdersByUserId(Long userAccountId, DefaultFilter filter) throws DaoException;

    /**
     * Finds List of {@link Order} by {@link com.ordjoy.entity.UserAccount} email from database
     *
     * @param email {@link com.ordjoy.entity.UserAccount} email in database
     * @return List of {@link Order}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<Order> findOrdersByUserEmail(String email, DefaultFilter filter) throws DaoException;

    /**
     * Finds List of {@link Order} by {@link com.ordjoy.entity.UserAccount} login from database
     *
     * @param login {@link com.ordjoy.entity.UserAccount} login in database
     * @return List of {@link Order}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<Order> findOrdersByUserLogin(String login, DefaultFilter filter) throws DaoException;

    /**
     * Finds List of {@link Order} by {@link com.ordjoy.entity.Track} id from database
     *
     * @param trackId {@link com.ordjoy.entity.Track} id in database
     * @return List of {@link Order}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<Order> findOrdersByTrackId(Long trackId, DefaultFilter filter) throws DaoException;

    /**
     * Finds List of {@link Order} by {@link com.ordjoy.entity.Track} name from database
     *
     * @param trackName {@link com.ordjoy.entity.Track} name in database
     * @return List of {@link Order}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<Order> findOrdersByTrackName(String trackName, DefaultFilter filter) throws DaoException;

    /**
     * Finds List of {@link Order} by {@link OrderStatus} from database
     *
     * @param orderStatus {@link Order} status in database
     * @return List of {@link Order}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<Order> findOrdersByOrderStatus(OrderStatus orderStatus, DefaultFilter filter) throws DaoException;

    /**
     * Gets all recorded fields from relevant table in database
     * @return Long value that represents all records in table
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    Long getTableRecords() throws DaoException;
}