package com.ordjoy.service;

import com.ordjoy.dao.filter.UserAccountFilter;
import com.ordjoy.dao.impl.UserDaoImpl;
import com.ordjoy.dto.UserDto;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.exception.DaoException;
import com.ordjoy.exception.ServiceException;

import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.*;

public class UserService {

    private final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static final UserService INSTANCE = new UserService();

    private UserService() {

    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public List<UserDto> findAllUsersWithLimitOffset(UserAccountFilter filter) throws ServiceException {
        List<UserDto> users;
        try {
            users = userDao.findAll(filter).stream()
                    .map(user -> new UserDto(
                            user.getId(),
                            user.getLogin(),
                            user.getEmail(),
                            user.getDiscountPercentageLevel()
                    ))
                    .collect(toList());
            return users;
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public UserAccount saveUser(UserAccount userAccount) throws ServiceException {
        try {
            return userDao.save(userAccount);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public Optional<UserDto> findUserById(Long id) throws ServiceException {
        Optional<UserDto> maybeUser;
        try {
            maybeUser = userDao.findById(id).stream()
                    .map(user -> new UserDto(
                            user.getId(),
                            user.getLogin(),
                            user.getEmail(),
                            user.getDiscountPercentageLevel()
                    )).findFirst();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return maybeUser;
    }

    public void updateUserData(UserAccount userAccount) throws ServiceException {
        try {
            userDao.update(userAccount);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public boolean deleteUserById(Long id) throws ServiceException {
        try {
            return userDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public void addDiscountPercentageLevel(Integer discountPercentageLevel, String userEmail) throws ServiceException {
        try {
            userDao.addDiscountPercentageLevel(discountPercentageLevel, userEmail);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public Optional<Integer> findDiscountPercentageLevelByUserId(Long userId) throws ServiceException {
        try {
            return userDao.findDiscountPercentageLevelByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public Optional<Integer> findDiscountPercentageLevelByEmail(String email) throws ServiceException {
        Optional<Integer> maybeDiscountPercentageLevel;
        try {
            maybeDiscountPercentageLevel = userDao.findDiscountPercentageLevelByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return maybeDiscountPercentageLevel;
    }

    public Optional<UserDto> findUserByLogin(String login) throws ServiceException {
        Optional<UserDto> maybeUser;
        try {
            maybeUser = userDao.findUserByLogin(login).stream()
                    .map(user -> new UserDto(
                            user.getId(),
                            user.getLogin(),
                            user.getEmail(),
                            user.getDiscountPercentageLevel()
                    )).findFirst();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return maybeUser;
    }

    public Optional<UserDto> findUserByEmail(String email) throws ServiceException {
        Optional<UserDto> maybeUser;
        try {
            maybeUser = userDao.findUserByEmail(email).stream()
                    .map(user -> new UserDto(
                            user.getId(),
                            user.getLogin(),
                            user.getEmail(),
                            user.getDiscountPercentageLevel()
                    )).findFirst();
            return maybeUser;
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }
}