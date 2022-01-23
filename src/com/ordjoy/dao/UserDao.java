package com.ordjoy.dao;

import com.ordjoy.dao.filter.UserAccountFilter;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.exception.DaoException;

import java.util.Optional;

public interface UserDao extends GenericDao<Long, UserAccount, UserAccountFilter> {

    void addDiscountPercentageLevel(Integer discountPercentageLevel, String userEmail) throws DaoException;

    Optional<Integer> findDiscountPercentageLevelByUserId(Long userId) throws DaoException;

    Optional<Integer> findDiscountPercentageLevelByEmail(String email) throws DaoException;

    Optional<UserAccount> findUserAccountByLoginAndPassword(String login, String password) throws DaoException;

    Optional<UserAccount> findUserByLogin(String login) throws DaoException;

    Optional<UserAccount> findUserByEmail(String email) throws DaoException;
}