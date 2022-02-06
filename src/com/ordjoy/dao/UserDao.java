package com.ordjoy.dao;

import com.ordjoy.dao.filter.UserAccountFilter;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.exception.DaoException;

import java.util.Optional;

public interface UserDao extends GenericDao<Long, UserAccount, UserAccountFilter> {

    /**
     * Updates {@link UserAccount} discount percentage level in database
     *
     * @param discountPercentageLevel new value of discount percentage level
     * @param userEmail               {@link UserAccount} email by which it will be found
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    void addDiscountPercentageLevel(Integer discountPercentageLevel, String userEmail) throws DaoException;

    /**
     * Find Discount percentage level from database by {@link UserAccount} id
     *
     * @param userId {@link UserAccount} id by which it will be found
     * @return {@link Optional} of {@link Integer} that presents discount level in percents (%)
     * if present or empty {@link Optional}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    Optional<Integer> findDiscountPercentageLevelByUserId(Long userId) throws DaoException;

    /**
     * Find Discount percentage level from database by {@link UserAccount} email
     *
     * @param email {@link UserAccount} id by which it will be found
     * @return {@link Optional} of {@link Integer} that presents discount level in percents (%)
     * if present or empty {@link Optional}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    Optional<Integer> findDiscountPercentageLevelByEmail(String email) throws DaoException;

    /**
     * Finds {@link UserAccount} in database by login & password
     *
     * @param login    {@link UserAccount} login
     * @param password {@link UserAccount} password
     * @return {@link Optional} of {@link UserAccount} that presents user in database
     * if present or empty {@link Optional}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    Optional<UserAccount> findUserAccountByLoginAndPassword(String login, String password) throws DaoException;

    /**
     * Finds {@link UserAccount} in database by login
     *
     * @param login {@link UserAccount} login
     * @return {@link Optional} of {@link UserAccount} that presents user in database
     * if present or empty {@link Optional}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    Optional<UserAccount> findUserByLogin(String login) throws DaoException;

    /**
     * Finds {@link UserAccount} in database by email
     *
     * @param email {@link UserAccount} email
     * @return {@link Optional} of {@link UserAccount} that presents user in database
     * if present or empty {@link Optional}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    Optional<UserAccount> findUserByEmail(String email) throws DaoException;
}